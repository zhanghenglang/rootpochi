package com.demo.pochi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 这里是可以用Page的
 * 但是我们这个分页查询是MongoDb的
 * 不需要那么多复杂的维度查询
 * 因此为了代码的可读性
 * 我们使用了实体类
 */

@Data
public class SysLogDto implements Serializable {

    /**
     * 日志编号
     */
    private Long logId;

    /**
     * 请求路径
     */
    private String logUrl;

    /**
     * 状态，1正常，0异常
     */
    private Integer logStatus;

    /**
     * 控制层
     * controller
     */
    private String logController;

    /**
     * 请求方式，GET、POST、PUT、DELETE
     */
    private String logMethod;

    /**
     * 响应时间（毫秒）
     */
    private Long logTime;

    /**
     * 请求ip
     */
    private String logIp;

    /**
     * 创建时间
     */
    private List<String> createdDate;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 页数
     */
    private Integer pageNumber;
}
