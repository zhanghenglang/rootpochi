package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品统计信息表
 */
@Data
public class ShopProductStatistic implements Serializable {

    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 收藏数
     */
    private Integer collectionCount;
    /**
     * 浏览数
     */
    private Integer historyCount;
    /**
     * 订单数
     */
    private Integer orderCount;
    /**
     * 评论数
     */
    private Integer commentCount;


}
