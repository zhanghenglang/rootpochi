package com.demo.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色视图类
 */
@Data
public class SysRoleVo implements Serializable {

    /**
     * 自增
     * 角色id
     * 自增->雪花算法/叶子算法->redis生成全局递增->自定义ID策略->UUID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 显示顺序
     */
    private  String roleSort;



    /**
     * 菜单ID集合
     */
    private List<Long> authIds;
}
