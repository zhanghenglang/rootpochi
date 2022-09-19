package com.demo.pochi.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Document(collection = "shop_product_history")
public class ShopProductHistory implements Serializable {
    @Id
    private Long id;

    /**
     * 商品编号
     */
    @Field("product_id")
    private Long productId;

    /**
     * 商品图片
     */
    @Field("product_pic")
    private String productPic;

    /**
     * 商品名称
     */
    @Field("product_name")
    private String productName;

    /**
     * 商品品牌名称
     */
    @Field("product_brand")
    private String productBrand;

    /**
     * 销售价格
     */
    @Field("product_price")
    private BigDecimal productPrice;

    /**
     * 商品分类ID
     */
    @Field("product_category_id")
    private Long productCategoryId;

    /**
     * 创建时间
     */
    @Field("create_time")
    private String createTime;

    /**
     * 创建日期(天数)
     */
    @Field("create_day")
    private String createDay;

    /**
     * 创建人
     */
    @Field("create_by")
    private String createBy;
}
