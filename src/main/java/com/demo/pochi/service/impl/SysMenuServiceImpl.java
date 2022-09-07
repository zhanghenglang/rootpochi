package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.constant.CoreConstant;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.exception.PochiException;
import com.demo.pochi.mapper.SysMenuMapper;
import com.demo.pochi.pojo.SysMenu;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.vo.RouterVo;
import com.demo.pochi.pojo.vo.SysMenuVo;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.SysMenuService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.ShiroUtils;
import com.demo.pochi.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public void save(SysMenu sysMenu) {

        //初始化默认值
        if (sysMenu.getParentId() == null){
            sysMenu.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //根据parentId和name查询该父级菜单下是否存在同名菜单
        SysMenu menu = sysMenuMapper.getByParentIdAndName(sysMenu);
        //如果存在，说明菜单已经存在
        if (menu != null){
            throw new PochiException(ResultEnum.MENU_EXISTS);
        }

        //菜单不存在就保存
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysMenu.setCreateBy(loginUser.getUsername());
        sysMenu.setUpdateBy(loginUser.getUsername());

        sysMenuMapper.save(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) {
        //初始化默认值
        if (sysMenu.getParentId() == null){
            sysMenu.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //根据parentId和name查询该父级菜单下是否存在同名菜单
        SysMenu menu = sysMenuMapper.getByParentIdAndName(sysMenu);
        //如果菜单存在，并且编号不相同，说明存在了同名的菜单
        if (menu != null && !menu.getMenuId().equals(sysMenu.getMenuId())){
            throw new PochiException(ResultEnum.MENU_EXISTS);
        }

        //菜单不存在就保存
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysMenu.setUpdateBy(loginUser.getUsername());
        sysMenuMapper.update(sysMenu);
    }

    @Override
    public void delete(Long id) {
        sysMenuMapper.deleteById(id);
    }

    @Override
    public SysMenu get(Long id) {
        return sysMenuMapper.getById(id);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<SysMenu> getByPage(Page<SysMenu> page) {
        // 设置默认的当前页和每页条数
        Integer pageNumber = page.getPageNumber();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            page.setPageNumber(pageNumber);
        }
        // 查询当前页数据
        List<SysMenu> list = sysMenuMapper.getByPage(page);
        // 查询总条数
        Integer totalCount = sysMenuMapper.countByPage(page);
        // 设置值
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<SysMenuVo> getTreeList() {

        //查询出所有的菜单
       List<SysMenu> menuList = sysMenuMapper.getAll();
        //过滤出所有的顶级菜单
        List<SysMenuVo> list = menuList.stream()
                //只要父级菜单为0的就是顶级菜单
                .filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                //将顶级菜单转换为我们的视图类
                .map(e -> {
                    SysMenuVo sysMenuvo = new SysMenuVo();
                    BeanUtils.copyProperties(e, sysMenuvo);
                    return sysMenuvo;
                })
                //根据顶级菜单的ID，递归从剩余的列表中找子菜单
                .map(e -> {
                    e.setChildren(getChildren(e, menuList));
                    //处理完之后，判断子菜单是否为空，给一个null
                    if (CollectionUtils.isEmpty(e.getChildren())) {
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());
        return list;
    }

    /**
     * 根据角色ID查询选中的菜单
     * 这里不查询父级菜单
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getRoleSelectMenu(Long roleId) {
        // 先查出来
        List<SysMenu> menuList = sysMenuMapper.getRoleSelectMenu(roleId);
        return menuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<RouterVo> getRouters() {
        //1. 查询出当前登录用户所拥有的启用中的所有菜单（权限不要查）
        LoginUser loginUser = ShiroUtils.getLoginUser();
        List<SysMenu> menuList = sysMenuMapper.getEnableMenuByUserId(loginUser.getId());
        //2. 构造成树形结构，也就是 SysMenuVo
        List<SysMenuVo> menuVoList = menuList.stream().filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                .map(e -> {
                    // 构造SysMenuVo
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    // 拷贝属性
                    BeanUtils.copyProperties(e, sysMenuVo);
                    return sysMenuVo;
                })
                .map(e -> {
                    // 构造树形结构
                    e.setChildren(getChildren(e, menuList));
                    if (e.getChildren() == null) {
                        // 设置为空集合
                        e.setChildren(new ArrayList<>(0));
                    }
                    return e;
                }).collect(Collectors.toList());
        //3. 构造成路由树

        return buildMenus(menuVoList);
    }


    /**
     * 构造路由树形结构
     *
     * @param menuVoList
     * @return
     */
    private List<RouterVo> buildMenus(List<SysMenuVo> menuVoList) {
        // 1. 遍历上面的菜单树
        return menuVoList.stream().map(e -> {
            // 2. 创建 `RouterVo` 对象，将菜单数据转换成路由视图对象
            RouterVo router = new RouterVo();
            router.setHidden(CoreConstant.HIDDEN_STATE.equals(e.getVisible()));
            router.setName(e.getMenuName());
            //    2.1. 构造跳转路径，用多个 '/' 拼接
            router.setPath(getRouterPath(e));
            //    2.2. 构造组件路径，用多个 '/' 拼接
            router.setComponent(getComponent(e));
            //    2.3. 构造meta数据
            router.setMeta(new RouterVo.MetaVo(e.getMenuName(), e.getIcon()));
            // 3. 如果当前是目录，并且子菜单不为空，就递归构造子菜单
            List<SysMenuVo> children = e.getChildren();
            if (!CollectionUtils.isEmpty(children) && StateEnums.FOLDER.getCode().equals(e.getMenuType())) {
                router.setAlwaysShow(true);
                // 设置redirect
                router.setRedirect(CoreConstant.NO_REDIRECT);
                // 递归构造菜单
                router.setChildren(buildMenus(children));
            } else {
                // 子菜单为空的情况下，children不能给null，否则会报错
                router.setChildren(new ArrayList<>(0));
            }
            return router;
        }).collect(Collectors.toList());
    }

    /**
     * 构造路由路径
     *
     * @param e
     * @return
     */
    private String getRouterPath(SysMenuVo e) {
        if(StateEnums.FOLDER.getCode().equals(e.getMenuType())) {
            return CoreConstant.URL_SPLIT + e.getRouterPath();
        }else {
            return e.getRouterPath();
        }
    }

    /**
     * 构造组件路径
     *
     * @param e
     * @return
     */
    private String getComponent(SysMenuVo e) {
        String component = CoreConstant.DEFAULT_COMPONENT;
        if(StringUtils.isNotEmpty(e.getComponentUrl())) {
            component = e.getComponentUrl();
        }
        return component;
    }

    /**
     * 递归构造树形菜单
     * @param sysMenu
     * @param menuList
     * @return
     */
    private List<SysMenuVo> getChildren(SysMenuVo sysMenu, List<SysMenu> menuList) {
        // 第一步，直接找到sysMenu的子菜单
        List<SysMenuVo> childrenList = menuList.stream().filter(e -> e.getParentId().equals(sysMenu.getMenuId()))
                // 第二步，把子菜单每一项转成SysMenuVo
                .map(e -> {
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    BeanUtils.copyProperties(e, sysMenuVo);
                    return sysMenuVo;
                })
                // 第三步，递归找到本次获取到的所有子菜单的子菜单
                .map(e -> {
                    e.setChildren(getChildren(e, menuList));
                    if (CollectionUtils.isEmpty(e.getChildren())) {
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());
        // 判断childrenList是不是空，如果是就返回null
        if (CollectionUtils.isEmpty(childrenList)) {
            // 这一步其实是过滤掉空集合
            return null;
        }
        return childrenList;
    }
}
