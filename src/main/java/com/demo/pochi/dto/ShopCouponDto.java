package com.demo.pochi.dto;

import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.ShopProductCategory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopCouponDto implements Serializable {
    /**
     * 编号，雪花算法
     */
    private Long id;

    /**
     * 使用类型，0全场通用，1指定分类，2指定商品
     */
    private Integer couponType;

    /**
     * 名称
     */
    private String name;

    /**
     * 优惠券金额
     */
    private BigDecimal amount;

    /**
     * 使用门槛
     */
    private BigDecimal minPoint;

    /**
     * 有效时间起
     */
    private String startTime;

    /**
     * 有效时间止
     */
    private String endTime;

    /**
     * 发行数量
     */
    private Integer publishCount;

    /**
     * 状态，1正常0过期
     */
    private Integer status;

    /**
     * 分类列表
     */
    private List<ShopProductCategory> categoryList;

    /**
     * 商品列表
     */
    private List<ShopProduct> productList;
}
