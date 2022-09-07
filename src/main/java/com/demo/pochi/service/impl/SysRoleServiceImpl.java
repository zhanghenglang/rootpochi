package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.mapper.SysRoleMapper;
import com.demo.pochi.mapper.SysRoleMenuMapper;
import com.demo.pochi.pojo.SysRole;
import com.demo.pochi.pojo.SysRoleMenu;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.vo.SysRoleVo;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.SysRoleService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    @Transactional(rollbackFor =Exception.class)
    public void save(SysRoleVo sysRole) {
        //设置创建人和修改人用户名账号
        LoginUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        // 创建SysRole对象
        SysRole role = new SysRole();
        // 拷贝属性
        BeanUtils.copyProperties(sysRole, role);
        role.setCreateBy(username);
        role.setUpdateBy(username);
        sysRoleMapper.save(role);
        // 下面开始添加角色权限数据
        saveRoleMenu(sysRole, role);
    }


    @Override
    @Transactional(rollbackFor =Exception.class)
    public void update(SysRoleVo sysRole) {
        // 设置更新人
        LoginUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        // 创建SysRole对象
        SysRole role = new SysRole();
        // 拷贝属性
        BeanUtils.copyProperties(sysRole, role);
        role.setUpdateBy(username);
        sysRoleMapper.update(role);
        // 删除当前角色的所有权限
        sysRoleMenuMapper.deleteByRoleId(role.getRoleId());
        // 下面开始添加角色权限数据
        saveRoleMenu(sysRole, role);

    }

    /**
     * 保存角色菜单数据
     * @param sysRole
     * @param role
     */
    private void saveRoleMenu(SysRoleVo sysRole, SysRole role) {
        if(!CollectionUtils.isEmpty(sysRole.getAuthIds())) {
            // 根据菜单ID集合去构建SysRoleMenu
            List<SysRoleMenu> roleMenuList = sysRole.getAuthIds().stream().map(id -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                // 设置菜单ID和角色ID
                sysRoleMenu.setMenuId(id);
                sysRoleMenu.setRoleId(role.getRoleId());
                return sysRoleMenu;
            }).collect(Collectors.toList());
            // 存库
            sysRoleMenuMapper.saveBatch(roleMenuList);
        }
    }

    @Override
    public void delete(long id) {
        sysRoleMapper.delete(id);
    }

    @Override
    public SysRoleVo get(long id) {

        SysRole sysRole = sysRoleMapper.get(id);
        // 拷贝属性
        SysRoleVo vo = new SysRoleVo();
        BeanUtils.copyProperties(sysRole, vo);
        // 查询这个角色存在的所有权限
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.getByRoleId(id);
        // 如果角色权限集合不为空，取出菜单ID集合
        if(!CollectionUtils.isEmpty(roleMenuList)) {
            // 取出权限ID集合
            List<Long> authIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            vo.setAuthIds(authIds);
        }
        return vo;

    }

    @Override
    public Page<SysRole> getByPage(Page<SysRole> page) {
        //设置默认当前页和每页条数
        Integer pageNumber = page.getPageNumber();
        if (pageNumber == null || pageNumber < 1){
            page.setPageNumber(1);
        }
        List<SysRole> sysRoleList = sysRoleMapper.getByPage(page);
        Integer totalCount = sysRoleMapper.countByPage(page);
        page.setList(sysRoleList);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();

    }
}
