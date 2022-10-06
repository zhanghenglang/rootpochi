package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.ShopCartItem;
import com.demo.pochi.service.ShopCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/cartItem")
@RestController
/**
 * 购物车
 */
public class ShopCartItemController {
    @Autowired
    private ShopCartItemService shopCartItemService;

    /**
     * 加入购物车
     * @param shopCartItem
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopCartItem shopCartItem) {
        shopCartItemService.save(shopCartItem);
        return new Result<>("加入成功");
    }

    /**
     * 获取当前登录用户购物车的商品数
     * @return
     */
    @RequestMapping(value = "/getProductCount", method = RequestMethod.GET)
    public Result<Integer>  getProductCount() {
        Integer count = shopCartItemService.getProductCount();
        return new Result<>(count);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<List<ShopCartItem>> getByPage(@RequestBody Page<ShopCartItem> page) {
        List<ShopCartItem> list = shopCartItemService.getByPage(page);
        return new Result<>(list);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.PUT)
    public Result<?> deleteByIds(@RequestBody List<Long> ids) {
        shopCartItemService.deleteByIds(ids);
        return new Result<>("删除成功");
    }

    /**
     * 移入收藏
     * @param ids
     * @return
     */
    @RequestMapping(value = "/moveToCollection", method = RequestMethod.POST)
    public Result<?> moveToCollection(@RequestBody List<Long> ids) {
        shopCartItemService.moveToCollection(ids);
        return new Result<>("移入成功");
    }



}
