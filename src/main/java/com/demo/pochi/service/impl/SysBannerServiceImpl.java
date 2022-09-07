package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.mapper.SysBannerMapper;
import com.demo.pochi.pojo.ShopBrand;
import com.demo.pochi.pojo.SysBanner;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.SysBannerService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysBannerServiceImpl implements SysBannerService {

    @Autowired
    private SysBannerMapper sysBannerMapper;
    @Override
    public void save(SysBanner sysBanner) {
        //获取当前登录人
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysBanner.setCreateBy(loginUser.getUsername());
        sysBanner.setUpdateBy(loginUser.getUsername());
        sysBannerMapper.save(sysBanner);

    }

    @Override
    public void update(SysBanner sysBanner) {
        //获取当前登录人
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysBanner.setUpdateBy(loginUser.getUsername());
        sysBannerMapper.update(sysBanner);

    }

    @Override
    public SysBanner get(Long id) {
        return sysBannerMapper.get(id);
    }

    @Override
    public void delete(Long id) {
        sysBannerMapper.delete(id);
    }

    @Override
    public void enable(Long id) {
        SysBanner banner = sysBannerMapper.get(id);
        banner.setStatus(StateEnums.ENABLED.getCode());
        sysBannerMapper.updateStatus(banner);
    }

    @Override
    public void disable(Long id) {
        SysBanner banner = sysBannerMapper.get(id);
        banner.setStatus(StateEnums.NOT_ENABLE.getCode());
        sysBannerMapper.updateStatus(banner);
    }

    @Override
    public Page<SysBanner> getByPage(Page<SysBanner> page) {
        //分页查询
        List<SysBanner> list = sysBannerMapper.getByPage(page);
        //查询总条数
        int totalCount = sysBannerMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }


}
