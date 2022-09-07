package com.demo.pochi.shiro;


import com.demo.pochi.pojo.ShopUser;
import com.demo.pochi.service.ShopUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shopUserRealm")
public class ShopUserRealm extends AuthorizingRealm {

    @Autowired
    private ShopUserService shopUserService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserToken userToken = (UserToken) authenticationToken;
        //拿到username也就是openid
        String openId = userToken.getUsername();
        //根据openid去查询用户
        ShopUser shopUser = shopUserService.getByOpenId(openId);
        if (shopUser == null) {
            throw new AuthenticationException("用户不存在！");
        }
        LoginUser loginUser = shopUser.toLoginUser();

        return new SimpleAuthenticationInfo(loginUser, shopUser.getOpenId(), getName());
    }
}
