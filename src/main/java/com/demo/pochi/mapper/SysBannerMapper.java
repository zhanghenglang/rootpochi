package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysBanner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysBannerMapper {
    /**
     * 保存
     *
     * @param sysBanner
     */
    void save(SysBanner sysBanner);

    /**
     * 修改
     *
     * @param sysBanner
     */
    void update(SysBanner sysBanner);

    /**
     * 修改状态
     *
     * @param sysBanner
     */
    void updateStatus(SysBanner sysBanner);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SysBanner get(Long id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<SysBanner> getByPage(Page<SysBanner> page);

    /**
     * 查询总数
     *
     * @param page
     * @return
     */
    int countByPage(Page<SysBanner> page);
}
