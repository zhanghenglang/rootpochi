package com.demo.pochi.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * 通知公告实体类
 */
@Data
public class SysNotice implements Serializable {

    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 排序值
     */
    private Integer sorted;

    private String createdBy;

    private String createdTime;

    /**
     * 是否删除，1是0否
     */
    private Integer delete;

    /**
     * 是否启用，1是0否
     */
    private Integer enabled;

}
