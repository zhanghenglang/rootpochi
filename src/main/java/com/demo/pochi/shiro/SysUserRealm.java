package com.demo.pochi.shiro;

import com.alibaba.fastjson.JSON;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.exception.PochiException;
import com.demo.pochi.mapper.SysMenuMapper;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

@Component("sysUserRealm")
public class SysUserRealm extends AuthorizingRealm {


    @Autowired
    private SysUserService userService;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户
        SysUserVo sysUserVo = (SysUserVo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(new HashSet<>(sysUserVo.getAuths()));
        return info;

    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //处理登录逻辑
        UserToken usernamePasswordToken = (UserToken) token;
        String username = usernamePasswordToken.getUsername();

        SysUser sysUser = userService.getByUsername(username);
        if (sysUser == null){
            throw new PochiException(ResultEnum.LOGIN_PARAM_ERROR);
        }

        //判断用户是否启用
        if (StateEnums.NOT_ENABLE.getCode().equals(sysUser.getStatus())){
            throw new PochiException(ResultEnum.LOGIN_PARAM_ERROR);
        }
        //判断用户是否已经被删除
        if (StateEnums.DELETED.getCode().equals(sysUser.getDeleted())){
            //已删除用户
            throw new PochiException(ResultEnum.LOGIN_PARAM_ERROR);
        }
        //创建SysUserVo，拷贝属性
        SysUserVo sysUserVo=new SysUserVo();
        BeanUtils.copyProperties(sysUser,sysUserVo);
        List<String> auths = sysMenuMapper.getMenuCodeByUserId(sysUser.getId());
        if (CollectionUtils.isEmpty(auths)){
            throw new PochiException("当前用户不具备任何权限，禁止登录");
        }
        sysUserVo.setAuths(auths);
        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(sysUserVo), LoginUser.class);

        return new SimpleAuthenticationInfo(loginUser,sysUser.getPassword(),this.getName());
    }
}