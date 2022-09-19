package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopProductDto;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.vo.ShopProductVo;

import java.util.List;

public interface ShopProductService {
    /**
     * 添加
     * @param shopProductDto
     */
    void save(ShopProductDto shopProductDto);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopProductVo> getByPage(Page<ShopProductVo> page);

    /**
     * 查询回显
     * @param id
     * @return
     */
    ShopProductVo getUpdate(Long id);

    /**
     * 修改
     * @param shopProductDto
     */
    void update(ShopProductDto shopProductDto);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 上架
     * @param id
     */
    void publish(Long id);

    /**
     * 下架
     * @param id
     */
    void unPublish(Long id);

    /**
     * 新品
     * @param id
     */
    void news(Long id);

    /**
     * 取消新品
     * @param id
     */
    void old(Long id);

    /**
     * 推荐
     * @param id
     */
    void recommend(Long id);

    /**
     * 取消推荐
     * @param id
     */
    void unRecommend(Long id);

    /**
     * 根据套装编号查询商品清单
     * @param packCode
     * @return
     */
    List<ShopProduct> getProductDetailList(Long packCode);

    /**
     * 分页查询，没有套装的商品
     * @param page
     * @return
     */
    Page<ShopProductVo> getByPageHasNotPack(Page<ShopProductVo> page);

    /**
     * 查询新品商品
     * @return
     */
    List<ShopProduct> getNewProduct();

    /**
     * 查询商品推荐
     * @return
     */
    List<ShopProduct> getRecommendList();

    /**
     * 根据id查询商品详情功能
     * @param id
     * @return
     */
    ShopProductVo get(Long id);

    /**
     * 排行榜
     * @param productId
     * @return
     */
    List<ShopProduct> getRankByProduct(Long productId);
}
