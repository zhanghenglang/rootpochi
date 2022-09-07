package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysRole;
import com.demo.pochi.pojo.vo.SysRoleVo;

import java.util.List;

public interface SysRoleService {
    /**
     * 新增
     * @param sysRole
     */
    void save(SysRoleVo sysRole);

    /**
     * 修改
     * @param sysRole
     */
    void update(SysRoleVo sysRole);

    /**
     * 删除
     * @param id
     */
    void delete(long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysRoleVo get(long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysRole> getByPage(Page<SysRole> page);

    /**
     * 查询所有角色信息
     * @return
     */
    List<SysRole> getAll();
}
