package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopPackDto;
import com.demo.pochi.pojo.ShopPack;

public interface ShopPackService {
    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopPack> getByPage(Page<ShopPack> page);

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
    ShopPackDto getById(Long id);

    /**
     * 修改
     * @param shopPackDto
     */
    void update(ShopPackDto shopPackDto);

    /**
     * 保存
     * @param shopPackDto
     */
    void save(ShopPackDto shopPackDto);
}
