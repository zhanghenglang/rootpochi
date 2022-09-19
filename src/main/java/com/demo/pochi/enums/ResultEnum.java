package com.demo.pochi.enums;


import lombok.Getter;

/**
 * 返回结果枚举
 */
@Getter
public enum  ResultEnum {
    /**
     * 返回结果枚举，每个枚举代表着一个返回状态
     */
    SUCCESS(20000, "操作成功！"),
    ERROR(40000, "操作失败！"),
    DATA_NOT_FOUND(40001, "查询失败！"),
    PARAMS_NULL(40002, "参数不能为空！"),
    PARAMS_ERROR(40005, "参数不合法！"),
    NOT_LOGIN(40006, "当前账号未登录！"),
    LOGIN_PARAM_ERROR(40007,"用户名或密码错误"),
    MENU_EXISTS(40008,"菜单已存在"),
    FILE_TYPE_ERROR(40009,"文件类型不支持"),
    DIR_TYPE_FOUND(40010,"文件不存在"),
    FILE_TOO_LARGE(40011,"文件过大"),
    AUTH_NOT_FOUNT(40012,"权限不足"),
    CATEGORY_EXISTS(40013,"分类已存在"),
    LOGIN_ERROR(40014,"登录失败"),
    USER_NOT_FOUND(40015,"用户不存在"),
    USER_REAL_EXISTS(40016,"；用户已存在");


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
