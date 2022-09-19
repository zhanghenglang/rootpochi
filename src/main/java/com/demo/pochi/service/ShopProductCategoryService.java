package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopProductCategory;
import com.demo.pochi.pojo.vo.ShopProductCategoryVo;

import java.util.List;

public interface ShopProductCategoryService {
    /**
     * 添加
     * @param shopProductCategory
     */
    void save(ShopProductCategory shopProductCategory);

    /**
     * 修改
     * @param shopProductCategory
     */
    void update(ShopProductCategory shopProductCategory);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ShopProductCategory get(Long id);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopProductCategory> getByPage(Page<ShopProductCategory> page);

    /**
     * 树形查询
     * @return
     */
    List<ShopProductCategoryVo> getTree();

    /**
     * 查询树形下拉框
     * @return
     */
    List<ShopProductCategoryVo> getSelectTree();

    /**
     * 查询所有二级商品分类
     * @return
     */
    List<ShopProductCategory> getAllSecond();

    /**
     * 查询首页分类最多展示
     * @return
     */
    List<ShopProductCategory> getNavList();
}
