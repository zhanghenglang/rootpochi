package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.pojo.SysUser;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.pojo.vo.TokenVo;
import com.demo.pochi.service.SysUserService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.shiro.SysUserRealm;
import com.demo.pochi.shiro.UserToken;
import com.demo.pochi.utils.ShiroUtils;
import com.demo.pochi.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 系统用户控制层
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 保存用户
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public  Result<?> save(@RequestBody SysUserVo sysUser){

        //校验参数
        if (StringUtils.isBlank(sysUser.getUsername())){
            return new Result<>(ResultEnum.PARAMS_NULL,"用户名不能为空");
        }
        if (StringUtils.isBlank(sysUser.getPassword())){
            return new Result<>(ResultEnum.PARAMS_NULL,"密码不能为空");
        }
        sysUserService.save(sysUser);
        return new Result<>("添加成功");
    }

    /**
     * 修改用户
     * 一般不提供密码修改
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public  Result<?> update(@RequestBody SysUserVo sysUser){
        sysUserService.update(sysUser);
        return new Result<>("修改成功");
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("sys:user:delete")
    public Result<?> delete(@PathVariable Long id) {
        sysUserService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 启用用户
     *
     * PathVariable:接收请求路径中占位符的值
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.PUT)
    public Result<?> enable(@PathVariable Long id) {
        sysUserService.enable(id);
        return new Result<>("启用成功");
    }


    /**
     * 禁用成功
     *
     * PathVariable:接收请求路径中占位符的值
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.PUT)
    public Result<?> disable(@PathVariable Long id) {
        sysUserService.disable(id);
        return new Result<>("禁用成功");
    }


    /**\
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage",method = RequestMethod.POST)
    public Result<Page<SysUser>> getByPage(@RequestBody Page<SysUser> page){

        page=sysUserService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysUserVo> get(@PathVariable Long id) {
        SysUserVo sysUser = sysUserService.get(id);
        return new Result<>(sysUser);
    }

    /**
     * 登录方法
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public Result<TokenVo> login(@RequestBody SysUser sysUser){
//        SysUser sysUser1 = sysUserService.getByUsername("admin");
        //校验用户名密码
        if (null == sysUser || StringUtils.isBlank(sysUser.getPassword()) || StringUtils.isBlank(sysUser.getUsername())){
            return new Result<>(ResultEnum.LOGIN_PARAM_ERROR);
        }
        //使用shiro进行登录
        Subject subject = SecurityUtils.getSubject();
//        AuthenticationToken authenticationToken = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
        AuthenticationToken authenticationToken = new UserToken(sysUser.getUsername(), sysUser.getPassword(), SysUserRealm.class);

        try {
            subject.login(authenticationToken);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(ResultEnum.LOGIN_PARAM_ERROR);
        }

        //登录成功，获取sessionId
        Serializable sessionId = subject.getSession().getId();

        //更新时间
        this.sysUserService.updateLoginTime(sysUser.getUsername());
        //返回sessionID
        return new Result<>(new TokenVo(sessionId));
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<LoginUser> info() {
        LoginUser sysUser = ShiroUtils.getLoginUser();
        sysUser.setPassword(null);
        sysUser.setStatus(null);

        return new Result<>(sysUser);
    }

    /**
     * 退出登录
     * 退出登录不能直接清除redis中的token，因为shiro中还有缓存
     * 清空shiro中的缓存
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result<SysUser> logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return new Result<>("退出成功");
    }

}


