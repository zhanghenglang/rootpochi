package com.demo.pochi.oss;

import com.aliyun.oss.OSS;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.exception.PochiException;
import com.demo.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * oss文件接口默认实现类
 */
@Service
@Slf4j
public class DefaultOssService implements OssService {

    @Autowired
    private OssConfig ossConfig;
    @Autowired
    private OSS oss;
    @Override
    public String upload(MultipartFile uploadFile, String dir) {
        // 校验图片格式
        boolean isLegal = false;
        String ext = uploadFile.getOriginalFilename();
        System.out.println(ext);
        for (String imageType : ossConfig.getImageTypes()) {
            // 判断如果存在，就把isLegal置为true
            if (StringUtils.endsWithIgnoreCase(ext, imageType)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            throw new PochiException(ResultEnum.FILE_TYPE_ERROR);
        }
        boolean dirFlag = false;
        // 校验文件夹是否存在
        for (String uploadDir : ossConfig.getUploadDirs()) {
            if (uploadDir.equals(dir)) {
                dirFlag = true;
                break;
            }
        }
        if (!dirFlag) {
            throw new PochiException(ResultEnum.DIR_TYPE_FOUND);
        }
        // 校验文件大小
        // 计算文件最大大小
        long maxSize = ossConfig.getMaxSize() * 1024 * 1024L;
        if (maxSize < uploadFile.getSize()) {
            throw new PochiException(ResultEnum.FILE_TOO_LARGE);
        }
        // 上传文件
        // 获取文件名 文件类型：ext
        String filename = getPath(ext, dir);
        // 上传文件
        try {
            oss.putObject(ossConfig.getBucketName(), filename, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (IOException e) {
            log.error("文件上传失败，", e);
            throw new PochiException("文件上传失败");
        }
        // 返回文件名
        return ossConfig.getViewUrl() + filename;
    }

    /**
     * 获取文件名
     *
     * @param ext
     * @param dir
     * @return
     */
    private String getPath(String ext, String dir) {
        return dir + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"
                + UUID.randomUUID().toString() + "." + ext;
    }
}
