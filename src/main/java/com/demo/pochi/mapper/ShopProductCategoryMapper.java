package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopProductCategoryMapper {
    /**
     * 添加
     *
     * @param shopProductCategory
     */
    void save(ShopProductCategory shopProductCategory);

    /**
     * 根据父级ID和名称查询
     *
     * @param shopProductCategory
     * @return
     */
    ShopProductCategory getByParentIdAndName(ShopProductCategory shopProductCategory);

    /**
     * 修改
     *
     * @param shopProductCategory
     */
    void update(ShopProductCategory shopProductCategory);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ShopProductCategory get(Long id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopProductCategory> getByPage(Page<ShopProductCategory> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopProductCategory> page);

    /**
     * 查询所有
     * @return
     */
    List<ShopProductCategory> getAll();

    /**
     * 查询所有一级二级分类
     * @return
     */
    List<ShopProductCategory> getSelectList();

    /**
     * 根据分类id集合查询
     * @param id
     * @return
     */
    List<ShopProductCategory> getByIds(List<Long> id);

    /**
     * 查询所有二级商品分类
     * @return
     */
    List<ShopProductCategory> getAllSecond();

    List<ShopProductCategory> getAllTop();
}
