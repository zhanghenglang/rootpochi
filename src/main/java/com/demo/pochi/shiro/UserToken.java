package com.demo.pochi.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 * 开闭原则
 */
public class UserToken extends UsernamePasswordToken {

    /**
     * 用户类型
     */
    private Class<? extends AuthorizingRealm> userType;

    public UserToken(String username, String password, Class<? extends AuthorizingRealm> userType) {
        super(username, password);
        this.userType = userType;
    }

    public Class<? extends AuthorizingRealm> getUserType() {
        return userType;
    }

    public void setUserType(Class<? extends AuthorizingRealm> userType) {
        this.userType = userType;
    }
}
