package com.demo.pochi.mapper;


import com.demo.pochi.common.Page;
import com.demo.pochi.pojo.SysHelp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysHelpMapper {
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
     * 根据id删除
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
    List<SysHelp> getByPage(Page<SysHelp> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysHelp> page);
}
