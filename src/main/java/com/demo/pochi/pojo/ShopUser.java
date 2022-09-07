package com.demo.pochi.pojo;


import com.demo.pochi.shiro.LoginUser;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员表实体类
 */
@Data
public class ShopUser  implements Serializable {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 状态，1正常0封禁
     */
    private Integer status;

    /**
     * 头像
     */
    private String header;

    /**
     * 性别，1男2女
     */
    private Integer gender;

    /**
     * 签名
     */
    private String note;

    /**
     * openid
     */
    private String openId;

    /**
     * 积分
     */
    private BigDecimal point;

    /**
     * 历史积分
     */
    private BigDecimal historyPoint;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

    /**
     * 转换为loginUser对象
     * @return
     */
    public LoginUser toLoginUser() {
        LoginUser user = new LoginUser();
        user.setId(id);
        user.setUsername(phone);
        user.setPassword(password);
        user.setNickName(nickname);
        user.setHeader(header);
        user.setOpenId(openId);
        return user;
    }
}
