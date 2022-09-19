package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopProductHistory;

import java.util.List;
import java.util.Map;

public interface ShopProductHistoryService {

    /**
     * 保存
     * @param shopProductHistory
     */
    void save(ShopProductHistory shopProductHistory);

    /**
     * 清空历史记录
     */
    void clearHistory();

    /**
     * 分页查询
     * @param page
     * @return
     */
    Map<String, List<ShopProductHistory>> getByPage(Page<ShopProductHistory> page);
}
