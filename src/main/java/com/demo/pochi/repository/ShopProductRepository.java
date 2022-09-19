package com.demo.pochi.repository;

import com.demo.pochi.pojo.ShopProductHistory;
import com.demo.pochi.pojo.SysLog;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ShopProductRepository extends MongoRepository<ShopProductHistory,String> {


}
