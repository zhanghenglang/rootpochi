package com.demo.pochi.mapper;


import com.demo.pochi.pojo.ShopProductStatistic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopProductStatisticMapper {


    /**
     *更新浏览数
     * @param id
     * @return
     */
    ShopProductStatistic getByProductId(Long id);

    /**
     * 保存浏览历史
     * @param id
     */
    void saveByProductId(Long id);

    /**
     * 根据商品编号查询
     * @param id
     */
    void updateHistory(Long id);

    void addCollectionCount(Long productId);

    void removeCollectionCount(Long productId);

    List<ShopProductStatistic> getByProductIds(List<Long> list);

}
