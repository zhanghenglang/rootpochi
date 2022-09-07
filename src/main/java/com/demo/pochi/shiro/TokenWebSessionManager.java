package com.demo.pochi.shiro;

import com.demo.pochi.constant.CoreConstant;
import com.demo.pochi.utils.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
@Configurable
public class TokenWebSessionManager extends DefaultWebSessionManager {


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
       //从请求头获取token
        String token = WebUtils.toHttp(request).getHeader(CoreConstant.TOKEN_HEADER);
        //如果token存在，就返回token，否则就生成一个
        if (StringUtils.isNotBlank(token)){
            return token;
        }
        return UUID.randomUUID().toString();

    }
}
