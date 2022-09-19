package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.constant.CoreConstant;
import com.demo.pochi.enums.ResultEnum;
import com.demo.pochi.exception.PochiException;
import com.demo.pochi.mapper.ShopProductCategoryMapper;
import com.demo.pochi.pojo.ShopProductCategory;
import com.demo.pochi.pojo.vo.ShopProductCategoryVo;
import com.demo.pochi.service.ShopProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopProductCategoryServiceImpl implements ShopProductCategoryService {
    @Autowired
    private ShopProductCategoryMapper shopProductCategoryMapper;


    /**
     * 添加
     * @param shopProductCategory
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopProductCategory shopProductCategory) {
        //若父级id为空，就赋值为0，代表根目录
       if (shopProductCategory.getParentId() == null){
            shopProductCategory.setParentId(CoreConstant.DEFAULT_PARENT_ID);
       }
       //根据parentId和name查询该父级菜单下是否存在同名菜单
        ShopProductCategory category = shopProductCategoryMapper.getByParentIdAndName(shopProductCategory);
       //若果存在说明该目录已存在
       if (category!=null){
           throw new PochiException(ResultEnum.CATEGORY_EXISTS);
       }
       shopProductCategoryMapper.save(shopProductCategory);


    }

    /**
     * 修改
     * @param shopProductCategory
     */
    @Override
    public void update(ShopProductCategory shopProductCategory) {
        //若父级id为空，就赋值为0，代表根目录
        if (shopProductCategory.getParentId() == null){
            shopProductCategory.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //如果没有改名称和父级菜单的情况下，当前查出来的就是自己，所有需要增加判断id是否相同
        ShopProductCategory category = shopProductCategoryMapper.getByParentIdAndName(shopProductCategory);

        if (category != null && !category.getId().equals(shopProductCategory.getId())) {
            throw new PochiException(ResultEnum.CATEGORY_EXISTS);
        }
        shopProductCategoryMapper.update(shopProductCategory);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public ShopProductCategory get(Long id) {
        return shopProductCategoryMapper.get(id);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        shopProductCategoryMapper.delete(id);
    }

    @Override
    public Page<ShopProductCategory> getByPage(Page<ShopProductCategory> page) {
        List<ShopProductCategory> list = shopProductCategoryMapper.getByPage(page);
        Integer totalCount = shopProductCategoryMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<ShopProductCategoryVo> getTree() {
        // 查询出所有
        List<ShopProductCategory> list = shopProductCategoryMapper.getAll();
        // 找到父节点
        return buildTree(list);
    }

    @Override
    public List<ShopProductCategoryVo> getSelectTree() {
        // 查询出所有非三级分类
        List<ShopProductCategory> list = shopProductCategoryMapper.getSelectList();
        return buildTree(list);
    }

    @Override
    public List<ShopProductCategory> getAllSecond() {
        //查询所有商品的二级分类
        List<ShopProductCategory> secondList = shopProductCategoryMapper.getAllSecond();
        //查询所有商品一级分类
        List<ShopProductCategory> topList=shopProductCategoryMapper.getAllTop();
        //遍历一级和二级分类
        secondList.forEach(s->{
            //遍历一级分类，取出id和s.parentId相同的数据
            ShopProductCategory parent = topList.stream().filter(t -> t.getId().equals(s.getParentId())).findFirst().orElse(null);
            if (parent != null){
                s.setName(parent.getName()+CoreConstant.CONCAT_NAME+s.getName());
            }
        });
         return secondList;
    }

    @Override
    public List<ShopProductCategory> getNavList() {
        return shopProductCategoryMapper.getNavList();
    }

    /**
     * 构造树形结构
     *
     * @param list
     * @return
     */
    private List<ShopProductCategoryVo> buildTree(List<ShopProductCategory> list) {
        return list.stream().filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                .map(e -> {
                    // 构造成VO
                    ShopProductCategoryVo vo = new ShopProductCategoryVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).map(e -> {
                    // 构造子节点
                    e.setChildren(getChildren(e, list));
                    //如果children是空就设置为null
                    if (CollectionUtils.isEmpty(e.getChildren())){
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());
    }

    /**
     * 寻找子节点
     *
     * @param category
     * @param list
     * @return
     */
    private List<ShopProductCategoryVo> getChildren(ShopProductCategoryVo category, List<ShopProductCategory> list) {
        // 找到category所有的子节点
        return list.stream().filter(e -> e.getParentId().equals(category.getId()))
                .map(e -> {
                    // 转成VO
                    ShopProductCategoryVo vo = new ShopProductCategoryVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).map(e -> {
                    e.setChildren(getChildren(e, list));
                    if (CollectionUtils.isEmpty(e.getChildren())){
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());
    }


}
