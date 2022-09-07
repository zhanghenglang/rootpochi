package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.dto.ShopPackDto;
import com.demo.pochi.pojo.ShopPack;
import com.demo.pochi.service.ShopPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopPack")
public class ShopPackController {
    @Autowired
    private ShopPackService shopPackService;

    /**
     * 添加
     * @param shopPackDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopPackDto shopPackDto) {
        shopPackService.save(shopPackDto);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     * @param shopPackDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody ShopPackDto shopPackDto) {
        shopPackService.update(shopPackDto);
        return new Result<>("修改成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<ShopPackDto> get(@PathVariable Long id) {
        ShopPackDto packDto = shopPackService.getById(id);
        return new Result<>(packDto);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        shopPackService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<ShopPack>> getByPage(@RequestBody Page<ShopPack> page) {
        page = shopPackService.getByPage(page);
        return new Result<>(page);
    }


}
