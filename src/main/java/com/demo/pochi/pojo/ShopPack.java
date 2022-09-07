package com.demo.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 套装表
 */
@Data
public class ShopPack implements Serializable {
    /**
     * 编号，雪花算法
     */
    private Long id;

    /**
     * 套装名称
     */
    private String name;

    /**
     * 商品数
     */
    private Integer productCount;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createBy;

}
