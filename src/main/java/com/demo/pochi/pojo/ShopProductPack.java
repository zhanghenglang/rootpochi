package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套装商品关联表
 */
@Data
public class ShopProductPack implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 商品编号
     */
    private Long productId;
    /**
     * 套装编号，同一编码下为同一套装的商品
     */
    private Long packCode;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     *库存
     */
    private Integer stock;
    /**
     * 预警库存
     */
    private Integer lowStock;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 销量
     */
    private Integer sale;
    /**
     * 商品名称
     */
    private String productName;
}
