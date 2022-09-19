package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysNotice;

import java.util.List;

public interface SysNoticeService {
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
     * 启用
     * @param id
     */
    void enable(Long id);

    /**
     * 禁用
     * @param id
     */
    void disable(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysNotice> getByPage(Page<SysNotice> page);

    /**
     * 首页查询公告通知
     * @return
     */
    List<SysNotice> getNoticeList();

}
