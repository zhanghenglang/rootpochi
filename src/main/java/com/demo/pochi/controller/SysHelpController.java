package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.SysHelp;
import com.demo.pochi.service.SysHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysHelp")
public class SysHelpController {

    @Autowired
    private SysHelpService sysHelpService;

    /**
     * 添加帮助
     * @param sysHelp
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysHelp sysHelp) {
        sysHelpService.save(sysHelp);
        return new Result<>("添加成功");
    }


    /**
     * 修改帮助
     * @param sysHelp
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysHelp sysHelp) {
        sysHelpService.update(sysHelp);
        return new Result<>("修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysHelpService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysHelp> get(@PathVariable Long id) {
        SysHelp sysHelp = sysHelpService.get(id);
        return new Result<>(sysHelp);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysHelp>> getByPage(@RequestBody Page<SysHelp> page) {
        page = sysHelpService.getByPage(page);
        return new Result<>(page);
    }
}
