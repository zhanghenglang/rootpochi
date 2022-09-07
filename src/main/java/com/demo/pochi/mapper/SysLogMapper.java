package com.demo.pochi.mapper;

import com.demo.pochi.pojo.SysLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysLogMapper {

    /**
     * 保存日志
     */
    void save(SysLog sysLog);


    List<SysLog> getAll();

}
