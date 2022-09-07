package com.demo.pochi.enums;

import lombok.Getter;

/**
 * 状态码枚举。所有的状态码都在这里编写
 */
@Getter
public enum StateEnums {
    /**
     * 逻辑删除状态
     */
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),

    /**
     * 启用状态
     */
    ENABLED(1, "启用"),
    NOT_ENABLE(0, "未启用"),

    /**
     * 性别状态
     */
    SEX_MAN(1, "男"),
    SEX_WOMAN(2, "女"),

    /**
     * 请求访问状态枚举
     */
    REQUEST_SUCCESS(1, "请求正常"),
    REQUEST_ERROR(0, "请求异常"),

    /**
     * 菜单状态枚举
     */
    FOLDER(1,"目录"),
    MENU(2,"菜单"),
    AUTH(3,"权限"),

    /**
     * 优惠券状态枚举
     *
     * 0全场通用，1指定分类，2指定商品
     */
    ALL(0,"全场通用"),
    CATEGORY(1,"指定分类"),
    PRODUCT(2,"指定商品"),

    /**
     * 绑定账户状态
     * 0:新号
     * 1：已存在
     */
    EXISTS_USER(1,"绑定已有账户"),
    NEW_USER(2,"绑定新账户");

    private Integer code;
    private String msg;

    StateEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
