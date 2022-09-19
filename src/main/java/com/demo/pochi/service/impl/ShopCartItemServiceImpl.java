package com.demo.pochi.service.impl;

import com.demo.pochi.mapper.ShopCartItemMapper;
import com.demo.pochi.mapper.ShopProductMapper;
import com.demo.pochi.pojo.ShopCartItem;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.service.ShopCartItemService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCartItemServiceImpl implements ShopCartItemService {
    @Autowired
    private ShopCartItemMapper shopCartItemMapper;
    @Autowired
    private ShopProductMapper shopProductMapper;
    @Override
    public void save(ShopCartItem shopCartItem) {
        //根据用户编号和商品编号查询当前用户是否已经将该商品加入购物车
        LoginUser loginUser = ShiroUtils.getLoginUser();
        shopCartItem.setCreateBy(loginUser.getUsername());
        ShopCartItem cartItem = shopCartItemMapper.getByProductIdAndCreateBy(shopCartItem);
        if (cartItem==null){
            //根据商品编号查询商品信息
            ShopProduct product = shopProductMapper.getInfoById(shopCartItem.getProductId());
            // 添加购物车
            shopCartItem.setPrice(product.getPrice());
            shopCartItem.setProductName(product.getName());
            shopCartItem.setProductPic(product.getPic());
            shopCartItemMapper.save(shopCartItem);
        }else {
            // 修改商品数
            shopCartItemMapper.updateProductCount(cartItem.getId());
        }

        //修改购物车商品数量
    }

    @Override
    public Integer getProductCount() {
        LoginUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        //根据用户名查询购物车商品数量
        return shopCartItemMapper.getProductCountByUser(username);
    }
}
