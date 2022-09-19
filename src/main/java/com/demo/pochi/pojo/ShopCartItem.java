package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopCartItem implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 添加到购物车的价格
     */
    private BigDecimal price;

    /**
     * 商品主图
     */
    private String productPic;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 状态，1有效0无效
     */
    private Integer status;

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
     * 是否删除，1是0否
     */
    private Integer deleted;
}
