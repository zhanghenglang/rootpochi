package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopCoupon;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopCouponMapper {
    /**
     * 添加
     * @param shopCoupon
     */
    void save(ShopCoupon shopCoupon);

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
    List<ShopCoupon> getByPage(Page<ShopCoupon> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopCoupon> page);

    /**
     * 查询全场通用优惠券
     * @return
     */
    List<ShopCoupon> getBothCoupon();

    /**
     * 根据分类ID查询优惠券
     * @param categoryId
     * @return
     */
    List<ShopCoupon> getByCategoryId(Long categoryId);

    /**
     * 根据商品ID查询优惠券
     * @param productId
     * @return
     */
    List<ShopCoupon> getByProductId(Long productId);
}
