package com.demo.pochi.controller;

import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.ShopCartItem;
import com.demo.pochi.service.ShopCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cartItem")
@RestController
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


}
