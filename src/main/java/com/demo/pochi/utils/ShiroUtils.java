package com.demo.pochi.utils;

import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.shiro.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;

/**
 * Shiro相关工具类
 *
 * @Author:
 * @Date: 2019/5/9 0009 下午 8:43
 * @Version 1.0
 */
public class ShiroUtils {

    private ShiroUtils() {
    }

    /**
     * 获取登录中的用户
     *
     * @return
     */
    public static LoginUser getLoginUser() {
        Session session = SecurityUtils.getSubject().getSession();
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (principalCollection == null) {
            return null;
        }
        return (LoginUser) principalCollection.getPrimaryPrincipal();
    }

    public static String getToken() {
        return (String) SecurityUtils.getSubject().getSession().getId();
    }

    /**
     * 切换身份，动态更改subject的用户属性
     *
     * @param userInfo
     */
    public static void setUser(Object userInfo) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection =
                new SimplePrincipalCollection(userInfo, realmName);
        subject.runAs(newPrincipalCollection);
        subject.getSession().setAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY", newPrincipalCollection);
    }
}
