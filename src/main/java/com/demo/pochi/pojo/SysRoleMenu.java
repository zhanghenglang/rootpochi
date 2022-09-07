package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleMenu implements Serializable {

    /**
     * Id
     * 主键，自增
     */
    private Long id;

    /**
     * 角色编号
     */
    private Long roleId;

    /**
     * 菜单编号
     */
    private Long menuId;
}
