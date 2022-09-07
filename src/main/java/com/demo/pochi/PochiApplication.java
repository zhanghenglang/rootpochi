package com.demo.pochi;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableTransactionManagement
@MapperScan("com.demo.pochi.mapper")
@EnableMongoRepositories(basePackages ="com.demo.pochi.*")
public class PochiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PochiApplication.class, args);
    }
    /**
     * 处理Long类型精度丢失
     * @return
     */
    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(Long.TYPE, ToStringSerializer.instance);
    }




}
