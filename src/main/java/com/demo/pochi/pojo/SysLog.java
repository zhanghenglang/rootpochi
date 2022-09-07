package com.demo.pochi.pojo;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


/**
 * 系统日志实体
 * 系统日志，存mongodb实体类
 */
@Data
@Document( "sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = -925216007678145752L;

    /**
     * 日志编号
     */
    @Id
    private String logId;

    /**
     * 请求路径
     */
    @Field("log_url")
    @Indexed
    private String logUrl;

    /**
     * 参数
     */
    @Field("log_params")
    private String logParams;

    /**
     * 状态，1正常，0异常
     */
    @Field("log_status")
    private Integer logStatus;

    /**
     * 异常文本
     */
    @Field("log_message")
    private String logMessage;

    /**
     * 浏览器ua标识
     */
    @Field("log_ua")
    private String logUa;

    /**
     * controller
     */
    @Field("log_controller")
    private String logController;

    /**
     * 请求方式，GET、POST、PUT、DELETE
     */
    @Field("log_method")
    private String logMethod;

    /**
     * 响应时间（毫秒）
     */
    @Field("log_time")
    private Long logTime;

    /**
     * 返回值
     */
    @Field("log_result")
    private String logResult;

    /**
     * 请求ip
     */
    @Field("log_ip")
    private String logIp;

    /**
     * 创建时间
     */
    @Field("created_date")
    private String createdDate;

    /**
     * 创建人
     */
    @Field("created_by")
    private String createdBy;

}
