package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopBrand;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopBrandMapper {


    /**
     * 添加
     * @param shopBrand
     */
    void save(ShopBrand shopBrand);

    /**
     * 修改
     * @param shopBrand
     */
    void update(ShopBrand shopBrand);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ShopBrand get(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopBrand> getByPage(Page<ShopBrand> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopBrand> page);

    /**
     * 根据分类id查询
     * @param categoryId
     * @return
     */
    List<ShopBrand> getByCategoryId(Long categoryId);

    /**
     * 根据品牌id集合查询实体
     * @param id
     * @return
     */
    List<ShopBrand> getByIds(List<Long> id);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<ShopBrand> getByName(String name);
}
