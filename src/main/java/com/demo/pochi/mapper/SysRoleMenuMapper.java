package com.demo.pochi.mapper;

import com.demo.pochi.pojo.SysRoleMenu;
import com.demo.pochi.pojo.SysUserRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色权限
 */
@Component
public interface SysRoleMenuMapper {


    void save(SysRoleMenu sysRoleMenu);

    /**
     * 根据菜单id删除
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 批量插入
     * @param roleMenu
     */
    void saveBatch(List<SysRoleMenu> roleMenu);

    /**
     * 根据角色ID查询
     * @param id
     * @return
     */
    List<SysRoleMenu> getByRoleId(long id);
}
