package com.demo.pochi.mapper;

import com.demo.pochi.pojo.ShopUserStatistic;
import org.springframework.stereotype.Component;

@Component
public interface ShopUserStatisticMapper {

    /**
     * 保存
     * @param statistic
     */
    void save(ShopUserStatistic statistic);
}
