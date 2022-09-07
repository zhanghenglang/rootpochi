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
}
