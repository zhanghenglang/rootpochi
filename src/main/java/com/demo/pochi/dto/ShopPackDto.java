package com.demo.pochi.dto;

import com.demo.pochi.pojo.ShopProduct;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopPackDto implements Serializable {
    /**
     * 编号，雪花算法
     */
    private Long id;

    /**
     * 套装名称
     */
    private String name;

    /**
     * 商品列表
     */
    private List<ShopProduct> productList;
}
