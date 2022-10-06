package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopCartItem;

import java.util.List;

public interface ShopCartItemService {
    /**
     * 加入到购物车
     * @param shopCartItem
     */
    void save(ShopCartItem shopCartItem);

    /**
     * 回去当前商品的购物车数量
     * @return
     */
    Integer getProductCount();

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopCartItem> getByPage(Page<ShopCartItem> page);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 移入收藏
     * @param ids
     */
    void moveToCollection(List<Long> ids);
}
