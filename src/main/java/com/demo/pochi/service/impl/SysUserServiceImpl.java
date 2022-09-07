package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.mapper.SysRoleMapper;
import com.demo.pochi.mapper.SysUserMapper;
import com.demo.pochi.mapper.SysUserRoleMapper;
import com.demo.pochi.pojo.SysRole;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.SysUserRole;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.SysUserService;
import com.demo.pochi.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public SysUser getByUsername(String username) {
        SysUser byUsername = sysUserMapper.getByUsername(username);
        return byUsername;
    }

    @Override
    public void updateLoginTime(String username) {
        sysUserMapper.updateLoginTime(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserVo sysUser) {
        //拷贝属性
        SysUser user= new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        long userId = idWorker.nextId();
        user.setId(userId);
        sysUserMapper.save(user);

        //判断角色id不等于空则需要存入角色用户表
        if (sysUser.getSysRole()!=null && sysUser.getSysRole().getRoleId() !=null){
            SysUserRole sysUserRole =  new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(sysUser.getSysRole().getRoleId());
            sysUserRoleMapper.save(sysUserRole);
        }
    }

    @Override
    public void update(SysUserVo sysUser) {
        //拷贝属性
        SysUser user= new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        sysUserMapper.update(user);

        //不管前端有没有选择角色信息，我们都把旧的角色信息删除，重新添加新的角色信息
        //此方法只适合 1对1 关系
        sysUserRoleMapper.deleteByUserId(user.getId());


        //判断角色id不等于空则需要存入角色用户表
        if (sysUser.getSysRole()!=null && sysUser.getSysRole().getRoleId() !=null){
            SysUserRole sysUserRole =  new SysUserRole();
            sysUserRole.setUserId(user.getId());
            sysUserRole.setRoleId(sysUser.getSysRole().getRoleId());
            sysUserRoleMapper.save(sysUserRole);
        }
    }

    @Override
    public void delete(Long id) {
        //删除用户
        sysUserMapper.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Long id) {
        //先查再修改，脏数据
         SysUser sysUser = sysUserMapper.getById(id);
         sysUser.setStatus(StateEnums.ENABLED.getCode()+"");
        sysUserMapper.updateStatus(sysUser);
    }

    /**
     * @Transactional注解可以作用于接口、接口方法、类以及类方法上
     * 1. 当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性
     * 2. 当作用在方法级别时会覆盖类级别的定义
     * 3. 当作用在接口和接口方法时则只有在使用基于接口的代理时它才会生效，也就是JDK动态代理，而不是Cglib代理
     * 4. 当在 protected、private 或者默认可见性的方法上使用 @Transactional 注解时是不会生效的，也不会抛出任何异常
     * 5. 默认情况下，只有来自外部的方法调用才会被AOP代理捕获，也就是，类内部方法调用本类内部的其他方法并不会引起事务行为，即使被调用方法使用@Transactional注解进行修饰
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(Long id) {
        //先查再修改，脏数据
        SysUser sysUser = sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.NOT_ENABLE.getCode()+"");
        sysUserMapper.updateStatus(sysUser);
    }

    @Override
    public Page<SysUser> getByPage(Page<SysUser> page) {

        //设置默认当前页和每页条数
        Integer pageNumber = page.getPageNumber();
        if (pageNumber == null || pageNumber < 1){
            pageNumber = 1;
            page.setPageNumber(pageNumber);
        }
        List<SysUser> userList = sysUserMapper.getByPage(page);
        Integer totalCount = sysUserMapper.countByPage(page);
        page.setList(userList);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public SysUserVo get(Long id) {
        SysUser user = sysUserMapper.getById(id);
        //拷贝属性
        SysUserVo sysUserVo= new SysUserVo();
        BeanUtils.copyProperties(user,sysUserVo);
        //查询角色信息
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.getByUserId(user.getId());
        if (!CollectionUtils.isEmpty(sysUserRoleList)){
            //说明有角色信息，去除角色ID
            List<Long> roleIds = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            //根据所有id去查询角色信息
             List<SysRole> roleList = sysRoleMapper.getByIds(roleIds);
             if (!CollectionUtils.isEmpty(roleList)){
                 sysUserVo.setSysRole(roleList.get(0));
             }
        }else {
            sysUserVo.setSysRole(new SysRole());
        }

        return sysUserVo;
    }
}
