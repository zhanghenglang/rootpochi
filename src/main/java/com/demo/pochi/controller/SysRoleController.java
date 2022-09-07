package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.SysRole;
import com.demo.pochi.pojo.vo.SysRoleVo;
import com.demo.pochi.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 新增角色
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysRoleVo sysRole){
        sysRoleService.save(sysRole);
    return new Result<>("添加成功");
    }

    /**
     * 修改角色
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysRoleVo sysRole){
        sysRoleService.update(sysRole);
    return new Result<>("修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable long id){
        sysRoleService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public Result<SysRoleVo> get(@PathVariable long id){
        SysRoleVo sysRole = sysRoleService.get(id);
        return new Result<>(sysRole);
    }

    /**\
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage",method = RequestMethod.POST)
    public Result<Page<SysRole>> getByPage(@RequestBody Page<SysRole> page){

        page=sysRoleService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 查询所有角色信息
     * @return
     */
    @RequestMapping(value = "/getAll" ,method = RequestMethod.GET)
    public Result<List<SysRole>> getAll(){

        List<SysRole> roleList = sysRoleService.getAll();
        return new Result<>(roleList);
    }

}
