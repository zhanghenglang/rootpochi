package com.demo.pochi.service;

import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysHelp;

public interface SysHelpService {
    /**
     * 添加
     * @param sysHelp
     */
    void save(SysHelp sysHelp);

    /**
     * 修改
     * @param sysHelp
     */
    void update(SysHelp sysHelp);

    /**
     * 根据 id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysHelp get(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysHelp> getByPage(Page<SysHelp> page);
}
