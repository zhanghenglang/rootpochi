package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券实体类
 */
@Data
public class ShopCoupon implements Serializable {
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
     * 剩余数量
     */
    private Integer restCount;

    /**
     * 发行数量
     */
    private Integer publishCount;

    /**
     * 使用数量
     */
    private Integer useCount;

    /**
     * 领取数量
     */
    private Integer receiveCount;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

    /**
     * 状态，1正常0过期
     */
    private Integer status;
}
