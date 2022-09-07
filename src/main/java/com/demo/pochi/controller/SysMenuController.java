package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.SysMenu;
import com.demo.pochi.pojo.vo.RouterVo;
import com.demo.pochi.pojo.vo.SysMenuVo;
import com.demo.pochi.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return new Result<>("添加成功");
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return new Result<>("修改成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id){
        sysMenuService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public Result<SysMenu> get(@PathVariable Long id){
        SysMenu sysMenu= sysMenuService.get(id);
        return new Result<>(sysMenu);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysMenu>> getByPage(@RequestBody Page<SysMenu> page) {
        page = sysMenuService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 树形结构查询
     */
    @RequestMapping(value = "/getTreeList",method = RequestMethod.GET)
    public Result<List<SysMenuVo>> getTreeList(){
        List<SysMenuVo> list = sysMenuService.getTreeList();

        return new Result<>(list);
    }

    /**
     * 根据角色ID查询选中的菜单
     * 这里不查询父级菜单
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/getRoleSelectMenu/{roleId}", method = RequestMethod.GET)
    public Result<List<Long>> getRoleSelectMenu(@PathVariable Long roleId) {
        List<Long> ids = sysMenuService.getRoleSelectMenu(roleId);
        return new Result<>(ids);
    }

    /**
     * 获取动态路由
     * @return
     */
    @RequestMapping(value = "/getRouters",method = RequestMethod.GET)
    public Result<List<RouterVo>> getRouters(){

        List<RouterVo> list=sysMenuService.getRouters();

        return new Result<>(list);
    }
}