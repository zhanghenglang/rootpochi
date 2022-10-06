package com.demo.pochi.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收藏表
 */

@Data
@Document(collection = "shop_product_collection")
public class ShopProductCollection implements Serializable {

    @Id
    private Long id;

    @Field("product_id")
    private Long productId;

    @Field("product_pic")
    private String productPic;

    @Field("product_name")
    private String productName;

    @Field("product_brand")
    private String productBrand;

    @Field("product_price")
    private BigDecimal productPrice;

    @Field("product_category_id")
    private Long productCategoryId;

    @Field("create_time")
    private String createTime;

    @Field("create_by")
    private String createBy;


}