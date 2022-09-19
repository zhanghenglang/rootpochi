package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.dto.ShopCouponDto;
import com.demo.pochi.pojo.ShopCoupon;
import com.demo.pochi.service.ShopCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券
 */
@RestController
@RequestMapping("/shopCoupon")
public class ShopCouponController {
    @Autowired
    private ShopCouponService shopCouponService;


    /**
     * 添加
     * @param shopCouponDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopCouponDto shopCouponDto) {
        shopCouponService.save(shopCouponDto);
        return new Result<>("添加成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        shopCouponService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 下架
     * @param id
     * @return
     */
    @RequestMapping(value = "/down/{id}", method = RequestMethod.PUT)
    public Result<?> down(@PathVariable Long id) {
        shopCouponService.down(id);
        return new Result<>("下架成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<ShopCoupon>> getByPage(@RequestBody Page<ShopCoupon> page) {
        page = shopCouponService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 查询指定商品可以使用的优惠券
     * 全场通用优惠券
     * 该商品所在分类的优惠券
     * 该商品的优惠券
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getProductCoupon/{productId}", method = RequestMethod.GET)
    public Result<List<ShopCoupon>> getProductCoupon(@PathVariable Long productId) {
        List<ShopCoupon> list = shopCouponService.getProductCoupon(productId);
        return new Result<>(list);
    }
}
