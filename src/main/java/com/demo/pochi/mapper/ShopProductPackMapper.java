package com.demo.pochi.mapper;

import com.demo.pochi.pojo.ShopProductPack;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopProductPackMapper {
    /**
     * 批量保存
     * @param packList
     */
    void saveBatch(List<ShopProductPack> packList);

    /**
     * 根据套装编号删除
     * @param packCode
     */
    void deleteByPackCode(long packCode);

    /**
     * 根据套装编号查询
     * @param id
     * @return
     */
    List<ShopProductPack> getByPackCode(Long id);

    /**
     * 根据商品id查询
     * @param id
     * @return
     */
    ShopProductPack getByProductId(Long id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据商品id集合删除关联关系
     * @param id
     */
    void deleteByProductIds(List<Long> id);

    /**
     * 根据商品id集合查询
     * @param id
     * @return
     */
    List<ShopProductPack> getByProductIds(List<Long> id);
}
