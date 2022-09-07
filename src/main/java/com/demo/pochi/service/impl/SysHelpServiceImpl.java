package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.mapper.SysHelpMapper;
import com.demo.pochi.pojo.SysHelp;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.SysHelpService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysHelpServiceImpl implements SysHelpService {

    @Autowired
    private SysHelpMapper sysHelpMapper;

    @Override
    public void save(SysHelp sysHelp) {
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysHelp.setCreatedBy(loginUser.getUsername());
        sysHelp.setUpdateBy(loginUser.getUsername());
        sysHelpMapper.save(sysHelp);
    }

    @Override
    public void update(SysHelp sysHelp) {
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysHelp.setUpdateBy(loginUser.getUsername());
        sysHelpMapper.update(sysHelp);
    }

    @Override
    public void delete(Long id) {
        sysHelpMapper.delete(id);
    }

    @Override
    public SysHelp get(Long id) {
        return sysHelpMapper.get(id);
    }

    @Override
    public Page<SysHelp> getByPage(Page<SysHelp> page) {
        List<SysHelp> list = sysHelpMapper.getByPage(page);
        Integer totalCount = sysHelpMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }
}
