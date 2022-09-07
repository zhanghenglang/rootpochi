package com.demo.pochi.shiro;

import com.alibaba.fastjson.JSON;
import com.demo.pochi.common.Result;
import com.demo.pochi.enums.ResultEnum;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 重写登录失效后重定向方法
 */
public class LoginFilter extends UserFilter {
    /**
     * 这个方法用于处理未登录时页面重定向的逻辑
     * 因此只要进入这个方法就意味着登录失效
     * 我们只需要在这里给前端返回一个登录失效的的状态即可
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        // 设置响应头是json
        response.setContentType("application/json; charset=utf-8");
        // 直接写回未登录的json报文
        response.getWriter().write(JSON.toJSONString(new Result<>(ResultEnum.NOT_LOGIN)));

    }
}
