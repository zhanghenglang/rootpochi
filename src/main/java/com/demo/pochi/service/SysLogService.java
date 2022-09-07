package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.SysLogDto;
import com.demo.pochi.pojo.SysLog;

/**
 * 日志
 */
public interface SysLogService {

    /**
     * 日志保存
     * @param sysLog
     */
    void save(SysLog sysLog);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysLog get(String id);

    void delete(String id);

    /**
     * 分页查询
     * @param sysLogDto
     * @return
     */
    Page<SysLog> getByPage(SysLogDto sysLogDto);
}
