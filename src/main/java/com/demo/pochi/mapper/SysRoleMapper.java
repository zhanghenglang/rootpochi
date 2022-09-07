package com.demo.pochi.mapper;


import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysRole;
import com.demo.pochi.pojo.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysRoleMapper {



    /**
     * 添加角色
     * @param sysRole
     */
    void save(SysRole sysRole);

    /**\
     * 修改角色
     * @param sysRole
     */
    void update(SysRole sysRole);

    /**
     * 删除用户
     * 逻辑删除
     *@param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysRole get(long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<SysRole> getByPage(Page<SysRole> page);

    /**
     * 查询总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysRole> page);

    List<SysRole> getByIds(List<Long> list);

    /**
     * 查询所有角色信息
     * @return
     */
    List<SysRole> getAll();
}
