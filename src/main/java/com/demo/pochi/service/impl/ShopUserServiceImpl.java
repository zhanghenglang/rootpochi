package com.demo.pochi.service.impl;

import com.demo.pochi.dto.ShopUserBindDto;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.exception.PochiException;
import com.demo.pochi.mapper.ShopUserMapper;
import com.demo.pochi.mapper.ShopUserStatisticMapper;
import com.demo.pochi.pojo.ShopUser;
import com.demo.pochi.pojo.ShopUserStatistic;
import com.demo.pochi.service.ShopUserService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopUserServiceImpl implements ShopUserService {

    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopUserStatisticMapper shopUserStatisticMapper;

    @Override
    public ShopUser getByOpenId(String openId) {

        return shopUserMapper.getByOpenId(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(ShopUser shopUser) {
        //用户入表
        long userId = idWorker.nextId();
        shopUser.setId(userId);
        shopUserMapper.save(shopUser);
        //创建用户统计对象入表
        ShopUserStatistic statistic = new ShopUserStatistic();
        long statisticId = idWorker.nextId();
        statistic.setId(statisticId);
        statistic.setUserId(userId);
        shopUserStatisticMapper.save(statistic);
    }

    @Override
    public ShopUser bindUser(ShopUserBindDto shopUserBindDto) {
        //获取绑定类型
        Integer bindType = shopUserBindDto.getBindType();
        if (StateEnums.NEW_USER.getCode().equals(bindType)) {
            // 校验手机号是否存在
            ShopUser shopUser = shopUserMapper.getByPhone(shopUserBindDto.getPhone());
            if (shopUser != null) {
                throw new PochiException(ResultEnum.USER_REAL_EXISTS);
            }
            // 获取当前登录用户，获取到openid，根据openid查询用户，设置 手机号、密码
            LoginUser loginUser = ShiroUtils.getLoginUser();
            String openId = loginUser.getOpenId();
            shopUser = shopUserMapper.getByOpenId(openId);
            shopUser.setPhone(shopUserBindDto.getPhone());
            shopUser.setPassword(shopUserBindDto.getPassword());
            shopUserMapper.updateLoginInfo(shopUser);
            return shopUser;

        }else {
            LoginUser loginUser = ShiroUtils.getLoginUser();
            // 绑定现有账户
            // 查询现有账户，更新openid
            ShopUser shopUser = shopUserMapper.getByPhone(shopUserBindDto.getPhone());
            if (shopUser == null) {
                throw new PochiException(ResultEnum.USER_NOT_FOUND);
            }
            shopUser.setOpenId(loginUser.getOpenId());
            shopUserMapper.updateLoginInfo(shopUser);
            // 删除当前登录用户ID对应的账号，这个账号是没有手机号的
            shopUserMapper.clearById(loginUser.getId());
            return shopUser;
        }
        }

}
