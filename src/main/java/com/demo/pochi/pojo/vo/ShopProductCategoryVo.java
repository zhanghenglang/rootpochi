package com.demo.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopProductCategoryVo  implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 是否显示在导航栏
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
     * 父级分类
     */
    private List<ShopProductCategoryVo> children;
}
