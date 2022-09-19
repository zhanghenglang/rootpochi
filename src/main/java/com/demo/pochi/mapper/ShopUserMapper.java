package com.demo.pochi.mapper;

import com.demo.pochi.pojo.ShopUser;
import org.springframework.stereotype.Component;

@Component
public interface ShopUserMapper {
    /**
     * 根据openId查询
     * @param openId
     * @return
     */
    ShopUser getByOpenId(String openId);

    /**
     * 注册用户
     * @param shopUser
     */
    void save(ShopUser shopUser);

    /**
     * 根据手机号查询用户是否存在
     * @param phone
     * @return
     */
    ShopUser getByPhone(String phone);

    void updateLoginInfo(ShopUser shopUser);

    /**
     *
     * 删除没有手机号的用户（根据id删除）
     * @param id
     */
    void clearById(Long id);
}
