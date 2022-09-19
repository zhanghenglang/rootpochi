package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopCouponDto;
import com.demo.pochi.pojo.ShopCoupon;

import java.util.List;

public interface ShopCouponService {

    /**
     * 添加
     * @param shopCouponDto
     */
    void save(ShopCouponDto shopCouponDto);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 下架
     * @param id
     */
    void down(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopCoupon> getByPage(Page<ShopCoupon> page);

    /**
     * 查询指定商品允许使用的优惠券
     * @param productId
     * @return
     */
    List<ShopCoupon> getProductCoupon(Long productId);
}
