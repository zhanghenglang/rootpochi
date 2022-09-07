package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 轮播图实体类
 */

@Data
public class SysBanner implements Serializable {

    /**
     * id,自增
     */
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String pic;

    /**
     * 状态 1启用 0弃用
     */
    private Integer status;

    /**
     * 点击数
     */
    private Integer clickCount;

    /**
     * 下单数
     */
    private Integer orderCount;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 备注
     */
    private String note;

    /**
     * 排序值
     */
    private String sort;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 逻辑删除，1是0否
     */
    private Integer deleted;


}
