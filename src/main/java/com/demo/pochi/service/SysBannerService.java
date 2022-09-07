package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopBrand;
import com.demo.pochi.pojo.SysBanner;

import java.util.List;

public interface SysBannerService {
    /**
     * 添加
     * @param sysBanner
     */
    void save(SysBanner sysBanner);

    /**
     * 修改
     * @param sysBanner
     */
    void update(SysBanner sysBanner);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysBanner get(Long id);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id启用
     * @param id
     */
    void enable(Long id);

    /**
     * 根据id弃用
     * @param id
     */
    void disable(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysBanner> getByPage(Page<SysBanner> page);


}
