package com.demo.pochi.pojo.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 登录token返回视图类
 */
@Data
public class TokenVo implements Serializable {

    /**
     * 登录时返回的token
     */
    private Serializable token;

    public TokenVo(Serializable token) {
        this.token = token;
    }
}
