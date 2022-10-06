package com.demo.pochi.repository;

import com.demo.pochi.pojo.ShopProductCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopProductCollectionRepository extends MongoRepository<ShopProductCollection, Long> {

    /**
     * 根据商品编号和创建人查询
     * @param productId 商品编号
     * @param createBy 创建人
     * @return
     */
    ShopProductCollection getByProductIdAndCreateBy(Long productId, String createBy);
}
