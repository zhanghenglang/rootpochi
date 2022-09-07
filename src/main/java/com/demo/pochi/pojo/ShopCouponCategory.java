package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类优惠券实体类
 */
@Data
public class ShopCouponCategory implements Serializable {

    private Long id;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 分类ID
     */
    private Long categoryId;

    public ShopCouponCategory(Long couponId, Long categoryId) {
        this.couponId = couponId;
        this.categoryId = categoryId;
    }
}
