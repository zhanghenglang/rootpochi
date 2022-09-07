package com.demo.pochi.mapper;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysNotice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysNoticeMapper {

    /**
     * 添加
     * @param sysNotice
     */
    void save(SysNotice sysNotice);

    /**
     * 修改
     * @param sysNotice
     */
    void update(SysNotice sysNotice);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysNotice get(Long id);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 更新启用状态
     * @param sysNotice
     */
    void updateEnable(SysNotice sysNotice);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysNotice> page);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<SysNotice> getByPage(Page<SysNotice> page);
}
