package com.demo.pochi.dto;

import com.demo.pochi.utils.IdWorker;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopUserBindDto implements Serializable {

    private String phone;

    private String password;

    /**
     * 绑定类型：
     * 1：绑定现有账户
     * 2：绑定新账户
     *
     */
    private Integer bindType;
}
