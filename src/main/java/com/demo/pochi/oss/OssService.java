package com.demo.pochi.oss;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 文件上传
     * @param uploadFile
     * @param dir 上传的目录
     * @return
     */
    String upload(MultipartFile uploadFile, String dir);
}
