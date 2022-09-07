package com.demo.pochi.service;

import com.demo.pochi.dto.ShopUserBindDto;
import com.demo.pochi.pojo.ShopUser;

public interface ShopUserService {
    /**
     * 根据账号查询用户
     * @param openId
     * @return
     */
    ShopUser getByOpenId(String openId);

    /**
     * 注册用户
     * @param toShopUser
     */
    void register(ShopUser toShopUser);

    /**
     * 绑定用户
     * @param shopUserBindDto
     * @return
     */
    ShopUser bindUser(ShopUserBindDto shopUserBindDto);
}
