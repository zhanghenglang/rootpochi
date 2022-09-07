package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品分类实体
 */
@Data
public class ShopProductCategory implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 父级ID  上级分类，0表示顶级分类
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 层级
     * 分类级别：1级，2级，3级。暂时只支持3级分类
     */
    private Integer level;

    /**
     * 是否显示在导航栏：1是0否
     */
    private Integer navStatus;

    /**
     * 排序，越小越靠前
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 逻辑删除 1是 0否
     */
    private Integer deleted;
}
