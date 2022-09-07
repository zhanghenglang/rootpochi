package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.vo.SysUserVo;


public interface SysUserService {


    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
     SysUser getByUsername(String username);

    /**
     * 更新指定用户的登陆时间
     * @param username
     */
    void updateLoginTime(String username);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUserVo sysUser);

    /**
     * 修改用户
     * @param sysUser
     */
    void update(SysUserVo sysUser);

    /**
     * 删除用户
     * @param id
     */
    void delete(Long id);

    /**
     * 启用用户
     * @param id
     */
    void enable(Long id);

    /**
     * 禁用用户
     * @param id
     */
    void disable(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysUser> getByPage(Page<SysUser> page);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysUserVo get(Long id);
}
