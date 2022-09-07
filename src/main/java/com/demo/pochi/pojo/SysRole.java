package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole implements Serializable {
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
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 删除标志（1代表删除 0代表存在）
     */
    private String deleted;

}
