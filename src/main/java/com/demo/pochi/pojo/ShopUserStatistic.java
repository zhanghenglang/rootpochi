package com.demo.pochi.pojo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopUserStatistic implements Serializable {
    /**
     * ID，雪花算法
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 累计消费金额
     */
    private BigDecimal consumeAmount;

    /**
     * 订单数量
     */
    private Integer orderCount;

    /**
     * 优惠券数量
     */
    private Integer couponCount;

    /**
     * 评价数
     */
    private Integer commentCount;

    /**
     * 退货数
     */
    private Integer returnOrderCount;

    /**
     * 登录次数
     */
    private Integer loginCount;
}
