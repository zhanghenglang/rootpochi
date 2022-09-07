package com.demo.pochi.mapper;

import com.demo.pochi.pojo.ShopBrandCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopBrandCategoryMapper {

    /**
     * 批量插入
     * @param brandCategoryList
     */
    void saveBatch(List<ShopBrandCategory> brandCategoryList);

    /**
     * 根据品牌ID删除
     * @param id
     */
    void deleteByBrandId(Long id);

    /**
     * 根据品牌ID查询
     * @param id
     * @return
     */
    List<ShopBrandCategory> getByBrandId(Long id);

    /**
     * 根据分类id查询品牌信息
     * @param categoryId
     * @return
     */
    List<ShopBrandCategory> getByCategoryId(Long categoryId);
}
