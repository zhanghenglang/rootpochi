package com.demo.pochi.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联
 */
@Data
public class SysUserRole implements Serializable {

    /**
     * 编号
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     *
     */
    private Long roleId;
}
