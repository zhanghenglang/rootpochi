package com.demo.pochi.controller;

import com.alibaba.fastjson.JSON;
import com.demo.pochi.common.Result;
import com.demo.pochi.config.WeChatConfig;
import com.demo.pochi.dto.ShopUserBindDto;
import com.demo.pochi.dto.WeChatRegisterDto;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.pojo.ShopUser;
import com.demo.pochi.pojo.WeChatResult;
import com.demo.pochi.pojo.vo.TokenVo;
import com.demo.pochi.service.ShopUserService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.shiro.ShopUserRealm;
import com.demo.pochi.shiro.UserToken;
import com.demo.pochi.utils.HttpUtils;
import com.demo.pochi.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/weChat")
public class WeChatController {
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private ShopUserService shopUserService;


    /**
     * 微信登录
     * @param code
     * @return
     */
    @RequestMapping(value = "/wxLogin/{code}",method = RequestMethod.GET)
    public Result<?> wxLogin(@PathVariable String code) throws Exception{

        //发送请求到微信服务器
        String loginBody = HttpUtils.get(weChatConfig.getAuthUrl(code)).body();
        //转换成WeChatResult
        WeChatResult weChatResult = JSON.parseObject(loginBody, WeChatResult.class);
        //使用shior登录
        Subject subject = SecurityUtils.getSubject();
        //我们约定，openid为username,unionid为password
        AuthenticationToken  userToken = new UserToken(weChatResult.getOpenId(),weChatResult.getUnionId(), ShopUserRealm.class);
        try {
            subject.login(userToken);
        }catch (Exception e){
            e.printStackTrace();
            // 说明openid对应用户不存在
            return new Result<>(weChatResult);
        }

        //获取sessionId
        Serializable token = subject.getSession().getId();
        return new Result<>(new TokenVo(token));
    }

    /**
     * 注册用户
     * @param weChatRegisterDto
     * @return
     */
    @RequestMapping(value = "/registerLogin", method = RequestMethod.POST)
    public Result<?> registerLogin(@RequestBody WeChatRegisterDto weChatRegisterDto) {
        //注册用户
        shopUserService.register(weChatRegisterDto.toShopUser());
        //  剩下的逻辑和登录一模一样
        // shiro登录
        Subject subject = SecurityUtils.getSubject();
        // 我们约定，openid为username，unionid为password
        AuthenticationToken authenticationToken = new UserToken(weChatRegisterDto.getOpenId(), weChatRegisterDto.getOpenId(), ShopUserRealm.class);
        try {
            subject.login(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultEnum.LOGIN_ERROR);
        }
        // 获取sessionId
        Serializable token = subject.getSession().getId();
        return new Result<>(new TokenVo(token));
    }

    /**
     * 绑定用户
     * @param shopUserBindDto
     * @return
     */
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public Result<?> bindUser(@RequestBody ShopUserBindDto shopUserBindDto){
        ShopUser shopUser = shopUserService.bindUser(shopUserBindDto);

        //转换成登录用户
        LoginUser loginUser = shopUser.toLoginUser();
        //更新当前登录用户
        ShiroUtils.setUser(loginUser);

        return new Result<>("绑定成功");
    }
}
