package com.demo.pochi.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfig {
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String viewUrl;

    private Integer maxSize;

    private List<String> uploadDirs;

    private List<String> imageTypes;

    @Bean
    public OSS oss() {
        OSSClientBuilder builder = new OSSClientBuilder();
        return builder.build(endpoint, accessKeyId, accessKeySecret);
    }
}
