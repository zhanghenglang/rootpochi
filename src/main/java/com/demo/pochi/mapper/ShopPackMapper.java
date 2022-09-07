package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.ShopPack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopPackMapper {
    /**
     * 保存
     * @param shopPack
     */
    void save(ShopPack shopPack);

    /**
     * 修改
     * @param shopPack
     */
    void update(ShopPack shopPack);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ShopPack get(Long id);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopPack> getByPage(Page<ShopPack> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopPack> page);

    /**
     * 更新商品数
     * @param packCode
     * @param size
     */
    void updateProductCount(@Param("packCode") long packCode, @Param("size") int size);
}
