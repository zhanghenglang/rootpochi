package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.vo.ShopProductVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopProductMapper {
    /**
     * 添加商品
     * @param shopProduct
     */
    void save(ShopProduct shopProduct);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopProduct> getByPage(Page<ShopProductVo> page);

    /**
     * 查询总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopProductVo> page);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ShopProduct getById(Long id);

    /**
     * 修改
     * @param shopProduct
     */
    void update(ShopProduct shopProduct);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 是否上架
     * @param product
     */
    void updatePublish(ShopProduct product);

    /**
     * 是否新品
     * @param product
     */
    void updateNewStatus(ShopProduct product);

    /**
     * 是否推荐
     * @param product
     */
    void updateRecommend(ShopProduct product);

    /**
     * 根据
     * @param id
     * @return
     */
    List<ShopProduct> getByIds(List<Long> id);

    /**
     * 分页查询，不存在套装的商品
     * @param page
     * @return
     */
    List<ShopProduct> getByPageHasNotPack(Page<ShopProductVo> page);

    /**
     * 不存在套装的商品总条数
     * @param page
     * @return
     */
    Integer countByPageHasNotPack(Page<ShopProductVo> page);

    /**
     * 查新新品商品
     * @return
     */
    List<ShopProduct> getNewProduct();

    /**
     * 查询推荐商品
     * @return
     */
    List<ShopProduct> getRecommendList();

    /**
     * 根据商品id查询商品详情信息
     * @param id
     * @return
     */
    ShopProduct getInfoById(Long id);

    /**
     * 查询在当前分类下销量最高的6条数据
     * @param categoryId
     * @return
     */
    List<ShopProduct> getRankByCategory(Long categoryId);
}
