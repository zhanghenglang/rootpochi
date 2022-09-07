package com.demo.pochi.constant;

/**
 * 核心常量
 */
public final class CoreConstant {
    /**
     * 请求头携带token的key
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 默认的父级菜单编号
     */
    public static final  Long DEFAULT_PARENT_ID=0L;

    /**
     * 不跳转
     */
    public static final  String NO_REDIRECT="noRedirect";

    /**
     * 路径间隔符
     */
    public static final  String URL_SPLIT="/";

    /**
     * 菜单默认组件地址
     */
    public static final  String DEFAULT_COMPONENT="Layout";

    /**
     * 菜单不显示
     */
    public static final  Integer HIDDEN_STATE=0;

    /**
     * 名称链接符
     */
    public static final String CONCAT_NAME=" -> ";

}
