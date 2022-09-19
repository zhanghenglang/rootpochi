package com.demo.pochi.service;

import com.demo.pochi.pojo.ShopCartItem;

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

}
