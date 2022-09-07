package com.demo.pochi.dto;

import com.demo.pochi.pojo.ShopPack;
import com.demo.pochi.pojo.ShopProduct;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopProductDto implements Serializable {

    /**
     * 商品ID
     * 雪花算法
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 商品副标题
     */
    private String subTitle;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 库存预警值
     */
    private Integer lowStock;

    /**
     * 购买积分
     */
    private BigDecimal point;

    /**
     * 轮播图片
     */
    private List<String> albumPicList;

    /**
     * 商品描述
     */
    private String productComment;

    /**
     * 详情
     */
    private String productContent;

    /**
     * 商品分类ID
     */
    private Long categoryId;

    /**
     * 套装编号
     */
    private Long packCode;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 运费
     */
    private BigDecimal transFee;

    /**
     * 规格
     */
    private String specs;

    /**
     * 是否上架
     */
    private Integer publishStatus;

    /**
     * 是否新品
     */
    private Integer newStatus;

    /**
     * 是否推荐
     */
    private Integer recommendStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 促销价格
     */
    private BigDecimal promotionPrice;

    /**
     * 套装
     */

    private ShopPack shopPack;

    /**
     * 商品列表
     */
    private List<ShopProduct> productList;


}
