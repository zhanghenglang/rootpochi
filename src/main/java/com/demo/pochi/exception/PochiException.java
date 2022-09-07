package com.demo.pochi.exception;

import com.demo.pochi.enums.ResultEnum;

/**
 * 自定义异常处理类
 */
public class PochiException extends RuntimeException {

    private Integer errCode= ResultEnum.ERROR.getCode();
    public PochiException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.errCode=resultEnum.getCode();
    }

    public PochiException(ResultEnum resultEnum,Throwable throwable){
        super(resultEnum.getMsg(),throwable);
        this.errCode=resultEnum.getCode();
    }

    public PochiException(Integer code,String msg){
        super(msg);
        this.errCode=code;
    }

    public PochiException(String msg){
        super(msg);
    }

    public PochiException(Throwable throwable){
        super(throwable);
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
