package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.dto.SysLogDto;
import com.demo.pochi.pojo.SysLog;
import com.demo.pochi.repository.SysLogRepository;
import com.demo.pochi.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */

@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public Result<SysLog> get(@PathVariable String id){
      SysLog sysLog= sysLogService.get(id);
      return new Result<>(sysLog);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable String id) {
        sysLogService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 分页查询
     * @param sysLogDto
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysLog>> getByPage(@RequestBody SysLogDto sysLogDto) {
        Page<SysLog> page = sysLogService.getByPage(sysLogDto);
        return new Result<>(page);

    }
}
