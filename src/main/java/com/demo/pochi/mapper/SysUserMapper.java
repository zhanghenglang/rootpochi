package com.demo.pochi.mapper;


import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysUserMapper {

    /**
     * 更新指定用户名的登陆时间为当前时间
     *
     * @param username
     */
    void updateLoginTime(String username);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysUser getByUsername(String username);

    /**
     * 添加用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**\
     * 修改用户
     * @param sysUser
     */
    void update(SysUser sysUser);

    /**
     * 删除用户
     * 逻辑删除
     *@param id
     */
    void delete(Long id);


    /**
     * 更新状态
     * @param sysUser
     */
    void updateStatus(SysUser sysUser);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysUser getById(Long id);

    /**
     * 根据page分页查询
     * @param page
     * @return
     */
    List<SysUser> getByPage(Page<SysUser> page);

    /**
     * 根据page查询总页数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysUser> page);
}
