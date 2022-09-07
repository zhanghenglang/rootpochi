package com.demo.pochi.mapper;

import com.demo.pochi.pojo.ShopCouponCategory;
import com.demo.pochi.pojo.ShopCouponProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopCouponCategoryMapper {
    /**
     * 批量插入
     * @param couponCategoryList
     */
    void saveBatch(List<ShopCouponCategory> couponCategoryList);


}
