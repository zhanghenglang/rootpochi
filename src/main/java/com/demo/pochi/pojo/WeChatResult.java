package com.demo.pochi.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatResult implements Serializable {
    @JSONField(name = "openid")
    private String openId;
    @JSONField(name = "session_key")
    private String sessionKey;
    @JSONField(name = "unionid")
    private String unionId;
    @JSONField(name = "errcode")
    private String errCode;
    @JSONField(name = "errmsg")
    private String errMsg;
}
