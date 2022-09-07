package com.demo.pochi.aop;

import com.alibaba.fastjson.JSON;
import com.demo.pochi.context.SystemContext;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.pojo.SysLog;
import com.demo.pochi.service.SysLogService;
import com.demo.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


/**
 *
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 声明切点
     */
    @Pointcut("execution( * com.demo..*.controller..*(..))")
    public void logPointCut(){

    }

    /**
     * 不记录日志的接口
     */
    private static final String[] EXCLUDE_URLS = {"/upload/"};


    /**
     * 判断URL是否需要放行
     * @param url
     * @return
     */
    private boolean exclude(String url) {
        for (String excludeUrl : EXCLUDE_URLS) {
            if(url.contains(excludeUrl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 前置通知
     * @param joinPoint
     * @throws Exception
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {

        //接受到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        //获取request
        HttpServletRequest request = attributes.getRequest();
        //获取请求地址
        String uri = request.getRequestURI();
        if (!exclude(uri)) {
        //记录日志
        //日志输出基本信息
        log.info("请求地址：{}", uri);
        log.info("请求方式：{}", request.getMethod());
        //获取请求ip
        String remoteIp = StringUtils.getRemoteIp(request);
        log.info("IP：{}", remoteIp);
        //获取请求的controller
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        log.info("方法：{}.{}", controllerName, joinPoint.getSignature().getName());
        //记录参数
        Object[] args = joinPoint.getArgs();
        //记录日志条件：参数不为空，并第一个参数不是request也不是MultipartFile
        boolean logParamFlag = args != null && args.length > 0 && !(args[0] instanceof ServletRequest) &&
                !(args[0] instanceof MultipartFile);
        SysLog sysLog = SystemContext.get().getSysLog();
        if (logParamFlag) {
            String param = JSON.toJSONString(args[0]);
            log.info("请求参数：{}", param);
            sysLog.setLogParams(param);
        }

        //记录日志
        sysLog.setLogUrl(uri);
        sysLog.setLogStatus(StateEnums.REQUEST_SUCCESS.getCode());
        sysLog.setLogMethod(request.getMethod());
        sysLog.setLogIp(remoteIp);
        sysLog.setLogUa(request.getHeader("user-Agent"));
        sysLog.setLogController(controllerName);
     }
    }

    /**
     * 环绕通知
     * @param pjp
     * @return
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        //接受到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        //获取request
        HttpServletRequest request = attributes.getRequest();
        //获取请求地址
        String uri = request.getRequestURI();


        //记录方法执行时间
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();
        long time = System.currentTimeMillis() - startTime;
        log.info("方法执行耗时：{}",time);

        if (!exclude(uri)) {
            SysLog sysLog = SystemContext.get().getSysLog();
            sysLog.setLogTime(time);

            //后置通知
            //获取返回值
            String result = JSON.toJSONString(ob);
            log.info("返回值：{}", result);
            sysLog.setLogResult(result);
//        sysLogService.save(sysLog);
        }
        SystemContext.get().remove();
        return ob;

    }
//    /**
//     * 后置通知,
//     *
//     * @param ret
//     */
//    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
//    public void doAfter(Object  ret) {
//        //获取返回值
//        String result = JSON.toJSONString(ret);
//        log.info("返回值：{}", result);
//        SysLog sysLog = SystemContext.get().getSysLog();
//        sysLog.setLogResult(result);
//        sysLogService.save(sysLog);
//        SystemContext.get().remove();
//    }

    /**
     * 异常通知，发生异常走这里
     *
     * @param joinPoint
     * @param throwable
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "throwable")
    public void doException(JoinPoint joinPoint, Throwable throwable) {

        //接受到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        //获取request
        HttpServletRequest request = attributes.getRequest();
        //获取请求地址
        String uri = request.getRequestURI();

        if (!exclude(uri)) {
            SysLog sysLog = SystemContext.get().getSysLog();
            sysLog.setLogStatus(StateEnums.REQUEST_ERROR.getCode());
            sysLog.setLogMessage(throwable.getMessage());
            sysLog.setLogTime(0L);
            sysLogService.save(sysLog);
        }
        SystemContext.get().remove();
    }

}
