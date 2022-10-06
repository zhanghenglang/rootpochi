package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopCartItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopCartItemMapper {
    /**
     * 据用户编号和商品编号查询当前用户是否已经将该商品加入购物车
     * @param shopCartItem
     * @return
     */
    ShopCartItem getByProductIdAndCreateBy(ShopCartItem shopCartItem);


    /**
     * 更新商品数
     *
     * @param id
     */
    void updateProductCount(Long id);

    /**
     * 添加
     * @param shopCartItem
     */
    void save(ShopCartItem shopCartItem);

    /**
     * 查询当前登录用户的购物车商品数
     * @param username
     * @return
     */
    Integer getProductCountByUser(String username);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopCartItem> getByPage(Page<ShopCartItem> page);

    /**
     * 批量删除
     * @param list
     */
    void deleteByIds(List<Long> list);
}
