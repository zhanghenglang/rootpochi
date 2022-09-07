package com.demo.pochi;

import com.demo.pochi.mapper.SysLogMapper;
import com.demo.pochi.oss.OssService;
import com.demo.pochi.pojo.SysLog;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.repository.SysLogRepository;
import com.demo.pochi.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@SpringBootTest
class PochiApplicationTests{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private OssService ossService;


    @Autowired
    SysLogRepository sysLogRepository;
    @Test
    public void contextLoads() {
        List<SysLog> sysLogList= sysLogMapper.getAll();
        sysLogRepository.saveAll(sysLogList);
    }


    @Test
    public void test() throws Exception {
        MultipartFile file = new MockMultipartFile("logo", "png", "", new FileInputStream(new File("D:\\app\\安装包\\资料\\【波奇商城】教学资源\\资料\\静态资源\\logo.png")));
        String header = ossService.upload(file, "header");
        System.out.println(header);
    }




}
