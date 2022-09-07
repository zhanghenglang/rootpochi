package com.demo.pochi.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类表 实体
 */
@Data
public class ShopBrandCategory implements Serializable {

    /**
     * 编号
     */
    private Long id;
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 分类ID
     */
    private Long categoryId;
}
