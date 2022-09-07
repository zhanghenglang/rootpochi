package com.demo.pochi.dto;


import com.demo.pochi.pojo.ShopUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatRegisterDto implements Serializable {
    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 性别 1男2女
     */
    private Integer gender;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * openId
     */
    private String openId;

    /**
     * 转换成会员对象
     * @return
     */
    public ShopUser toShopUser() {
        ShopUser shopUser = new ShopUser();
        shopUser.setHeader(avatarUrl);
        shopUser.setGender(gender);
        shopUser.setNickname(nickName);
        shopUser.setOpenId(openId);
        return shopUser;
    }
}
