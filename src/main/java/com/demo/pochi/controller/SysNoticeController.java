package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.SysNotice;
import com.demo.pochi.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysNotice")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 添加
     *
     * @param sysNotice
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysNotice sysNotice) {
        sysNoticeService.save(sysNotice);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     *
     * @param sysNotice
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysNotice sysNotice) {
        sysNoticeService.update(sysNotice);
        return new Result<>("修改成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysNotice> get(@PathVariable Long id) {
        SysNotice sysNotice = sysNoticeService.get(id);
        return new Result<>(sysNotice);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysNoticeService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.PUT)
    public Result<?> enable(@PathVariable Long id) {
        sysNoticeService.enable(id);
        return new Result<>("启用成功");
    }

    /**
     * 禁用
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.PUT)
    public Result<?> disable(@PathVariable Long id) {
        sysNoticeService.disable(id);
        return new Result<>("禁用成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysNotice>> getByPage(@RequestBody Page<SysNotice> page) {
        page = sysNoticeService.getByPage(page);
        return new Result<>(page);
    }
}
