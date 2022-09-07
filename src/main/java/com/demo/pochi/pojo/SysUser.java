package com.demo.pochi.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 */
@Data
public class SysUser implements Serializable {

    /**
     * id，用雪花算法设置id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 微信的openid
     */
    private String openId;

    /**
     *密码
     */
    private String password;

    /**
     *邮箱
     */
    private String email;

    /**
     *昵称
     */
    private String nickName;

    /**
     *头像
     */
    private String header;

    /**
     *备注
     */
    private String note;

    /**
     *创建时间
     */
    private String createTime;

    /**
     *修改时间
     */
    private Date updateTime;

    /**
     *最后登陆时间
     */
    private String loginTime;

    /**
     *账号启用状态  1是0否
     */
    private String status;

    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

}
