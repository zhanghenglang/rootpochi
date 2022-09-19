package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.ShopProductHistory;
import com.demo.pochi.service.ShopProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 浏览记录
 */
@RequestMapping("/history")
@RestController
public class ShopProductHistoryController {
    @Autowired
    private ShopProductHistoryService shopProductHistoryService;

    /**
     * 保存
     *
     * @param shopProductHistory
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopProductHistory shopProductHistory) {
        shopProductHistoryService.save(shopProductHistory);
        return new Result<>("添加成功");
    }

    /**
     * 清空历史
     *
     * @return
     */
    @RequestMapping(value = "/clearHistory", method = RequestMethod.DELETE)
    public Result<?> clearHistory() {
        shopProductHistoryService.clearHistory();
        return new Result<>("清空成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Map<String, List<ShopProductHistory>>> getByPage(@RequestBody Page<ShopProductHistory> page) {
        Map<String, List<ShopProductHistory>> data = shopProductHistoryService.getByPage(page);
        return new Result<>(data);
    }

}
