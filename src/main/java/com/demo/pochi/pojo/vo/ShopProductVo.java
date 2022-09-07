package com.demo.pochi.pojo.vo;

import com.demo.pochi.pojo.ShopPack;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.ShopProductCategory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopProductVo  implements Serializable {

    /**
     * 商品ID
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
     * 销量
     */
    private Integer sale;

    /**
     * 评论数
     */
    private Integer commentNum;

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
     * 商品分类ID集合
     */
    private List<Long> categoryIds;

    /**
     * 商品分类
     */
    private ShopProductCategory category;

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
     * 优点：这里存储了品牌名称，那么在页面展示商品属于哪个品牌时，直接展示即可，不需要多连一张表耗费性能
     * 当一张表的某个字段可以确定基本上不可能被修改时 ，这个字段在设计数据库的时候就允许被冗余到其他表中
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
     * 是否审核
     */
    private Integer verifyStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 促销价格
     */
    private BigDecimal promotionPrice;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 套装商品
     */
    private List<ShopProduct> productList;

    /**
     * 所在套装
     */
    private ShopPack shopPack;

}
