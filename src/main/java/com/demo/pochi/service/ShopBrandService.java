package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopBrandDto;
import com.demo.pochi.pojo.ShopBrand;
import com.demo.pochi.pojo.vo.ShopBrandVo;

import java.util.List;

public interface ShopBrandService {

    /**
     * 添加
     * @param shopBrandDto
     */
    void save(ShopBrandDto shopBrandDto);

    /**
     * 修改
     * @param shopBrandDto
     */
    void update(ShopBrandDto shopBrandDto);

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
    ShopBrandVo get(Long id);

    /**
     * 查询用于回显的数据
     * @param id
     * @return
     */
    ShopBrandDto getUpdate(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopBrand> getByPage(Page<ShopBrand> page);

    /**
     * 根据商品分类查询品牌
     * @param categoryId
     * @return
     */
    List<ShopBrand> getByCategoryId(Long categoryId);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<ShopBrand> getByName(String name);
}
