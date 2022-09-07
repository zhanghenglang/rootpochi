package com.demo.pochi.mapper;



import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface SysMenuMapper {


    /**
     * 根据父级菜单和名称查询是否同级菜单下有相同名称的子菜单名称
     * @param sysMenu
     * @return
     */
    SysMenu getByParentIdAndName(SysMenu sysMenu);

    /**
     * 添加菜单
     * @param sysMenu
     */
    void save(SysMenu sysMenu);

    /**
     * 修改菜单
     * @param sysMenu
     */
    void update(SysMenu sysMenu);

    /**
     * 删除菜单
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysMenu getById(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<SysMenu> getByPage(Page<SysMenu> page);

    /**
     * 查询总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysMenu> page);

    /**
     * 查询所有的菜单
     * @return
     */
    List<SysMenu> getAll();

    /**
     * 根据角色id查询
     * @param roleId
     * @return
     */
    List<SysMenu> getRoleSelectMenu(Long roleId);

    /**
     * 根据用户id查询启用中的菜单（权限查）
     * @param id
     * @return
     */
    List<SysMenu> getEnableMenuByUserId(Long id);

    /**
     * 根据用户id，查询权限功能
     *
     * 权限标识不为空并且用户拥有的权限
     * @param userId
     * @return
     */
    List<String> getMenuCodeByUserId(Long userId);

    /**
     * 根据用户id查询用户权限信息
     * @param id
     * @return
     */
    List<SysMenu> getAuthByUserId(@Param("id") Long id);
}
