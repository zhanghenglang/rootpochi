package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 品牌实体类
 */
@Data
public class ShopBrand implements Serializable {


    /**
     * 编号
     */
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 是否显示
     */
    private Integer showStatus;
    /**
     * logo
     */
    private String logo;
    /**
     * 逻辑删除 1是0否
     */
    private Integer deleted;
}
