package com.demo.pochi.mapper;

import com.demo.pochi.pojo.SysUserRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户角色
 */
@Component
public interface SysUserRoleMapper {


    void save(SysUserRole sysUserRole);

    /**
     * 根据用户id删除
     * @param id
     */
    void deleteByUserId(Long id);

    /**
     * 根据用户id查询用户角色关联信息
     * @param id
     * @return
     */
    List<SysUserRole> getByUserId(Long id);
}
