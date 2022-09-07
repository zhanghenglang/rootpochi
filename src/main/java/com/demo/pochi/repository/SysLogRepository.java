package com.demo.pochi.repository;

import com.demo.pochi.pojo.SysLog;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface SysLogRepository extends MongoRepository<SysLog,String> {


}
