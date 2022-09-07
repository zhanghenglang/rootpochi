package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysMenu;
import com.demo.pochi.pojo.vo.RouterVo;
import com.demo.pochi.pojo.vo.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void delete(Long id);

    SysMenu get(Long id);

    Page<SysMenu> getByPage(Page<SysMenu> page);

    List<SysMenuVo> getTreeList();

    /**
     * 根据角色ID查询被选中的菜单ID集合
     * @param roleId
     * @return
     */
    List<Long> getRoleSelectMenu(Long roleId);

    /**
     * 获取动态路由
     * @return
     */
    List<RouterVo> getRouters();

}
