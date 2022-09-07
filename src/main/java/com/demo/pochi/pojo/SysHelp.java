package com.demo.pochi.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * 帮助中心实体类
 */
@Data
public class SysHelp  implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 帮助内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

}
