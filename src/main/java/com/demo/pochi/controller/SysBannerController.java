package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.ShopBrand;
import com.demo.pochi.pojo.SysBanner;
import com.demo.pochi.service.SysBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制类
 */

@RestController
@RequestMapping("/sysBanner")
public class SysBannerController {

    @Autowired
    private SysBannerService sysBannerService;

    /**
     * 添加
     * @param sysBanner
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysBanner sysBanner){
        sysBannerService.save(sysBanner);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     *
     * @param sysBanner
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysBanner sysBanner) {
        sysBannerService.update(sysBanner);
        return 	new Result<>("修改成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysBanner> get(@PathVariable Long id) {
        SysBanner sysBanner = sysBannerService.get(id);
        return new Result<>(sysBanner);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysBannerService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.PUT)
    public Result<?> enable(@PathVariable Long id) {
        sysBannerService.enable(id);
        return new Result<>("启用成功");
    }

    /**
     * 根据id弃用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.PUT)
    public Result<?> disable(@PathVariable Long id) {
        sysBannerService.disable(id);
        return new Result<>("弃用成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysBanner>> getByPage(@RequestBody Page<SysBanner> page) {
        page = sysBannerService.getByPage(page);
        return new Result<>(page);
    }


}
