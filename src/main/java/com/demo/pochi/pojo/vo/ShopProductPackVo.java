package com.demo.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopProductPackVo implements Serializable {

    /**
     * 编号，自增
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 套装编号
     */
    private Long packCode;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 规格
     */
    private String specName;

    /**
     * 商品名称
     */
    private String productName;

}
