package com.demo.pochi.advice;

import com.demo.pochi.common.Result;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.exception.PochiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理
 */
@RestController
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 全局处理自定义异常
     * @param pochiException
     * @return
     */
    @ExceptionHandler(PochiException.class)
    public Result<?> exceptionHandler(PochiException pochiException){
        log.error("统一异常处理",pochiException);
         return new Result<>(pochiException.getErrCode(),pochiException.getMessage());
    }

    /**
     * 处理权限不足异常
     * @param exception
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public  Result<?> authorizationHandler(AuthorizationException exception){
        log.error("权限不足异常处理",exception);
        return new Result<>(ResultEnum.AUTH_NOT_FOUNT);
    }
}
