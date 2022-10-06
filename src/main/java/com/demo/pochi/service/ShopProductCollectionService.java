package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopProductCollection;
import com.demo.pochi.pojo.vo.ShopProductCollectionVo;

public interface ShopProductCollectionService {
    /**
     * 切换收藏状态
     * @param shopProductCollection
     */
    void changeCollection(ShopProductCollection shopProductCollection);

    /**
     * 根据商品Id查询
     * @param productId
     * @return
     */
    ShopProductCollection getByProductId(Long productId);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopProductCollectionVo> getByPage(Page<ShopProductCollection> page);
}
