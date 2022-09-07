package com.demo.pochi.controller;


import com.demo.pochi.common.Result;
import com.demo.pochi.oss.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private OssService ossService;

    /**
     * 文件上传
     *
     * @param file
     * @param dir
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result<String> uploadFile(MultipartFile file, String dir) {
        String upload = ossService.upload(file, dir);
        return new Result<>("上传成功", upload);
    }
}
