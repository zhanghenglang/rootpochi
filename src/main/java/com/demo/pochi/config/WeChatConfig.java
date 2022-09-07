package com.demo.pochi.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {
    private String appId;
    private String appSecret;
    private String grantType;
    private String loginUrl;

    /**
     * 获取code
     * @param code
     * @return
     */
    public String getAuthUrl(String code) {
        // https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        return loginUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=" + grantType;
    }
}
