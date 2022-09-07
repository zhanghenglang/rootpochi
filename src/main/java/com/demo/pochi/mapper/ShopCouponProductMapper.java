package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopCoupon;
import com.demo.pochi.pojo.ShopCouponProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopCouponProductMapper {
    /**
     * 批量插入
     * @param couponProductList
     */
    void saveBatch(List<ShopCouponProduct> couponProductList);

}
