package com.demo.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopProductCollectionVo implements Serializable {
    private Long id;

    private Long productId;

    private String productPic;

    private String productName;

    private String productBrand;

    private BigDecimal productPrice;

    private Long productCategoryId;

    private String createTime;

    private String createBy;

    /**
     * 收藏数
     */
    private Integer collectionCount;
}
