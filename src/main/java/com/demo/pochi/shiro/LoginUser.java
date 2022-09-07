package com.demo.pochi.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginUser implements Serializable {
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private  String password;
    /**
     *账号启用状态  1是0否
     */
    private String status;

    /**
     * 微信小程序openid
     */
    private String openId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String header;

    /**
     * 最后登录时间
     */
    private String loginTime;

    /**
     * 权限列表
     */
    private List<String> auths;

}
