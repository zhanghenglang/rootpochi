package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopBrandDto;
import com.demo.pochi.mapper.ShopBrandCategoryMapper;
import com.demo.pochi.mapper.ShopBrandMapper;
import com.demo.pochi.mapper.ShopProductCategoryMapper;
import com.demo.pochi.pojo.ShopBrand;
import com.demo.pochi.pojo.ShopBrandCategory;
import com.demo.pochi.pojo.ShopProductCategory;
import com.demo.pochi.pojo.vo.ShopBrandVo;
import com.demo.pochi.service.ShopBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopBrandServiceImpl implements ShopBrandService {


    @Autowired
    private ShopBrandMapper shopBrandMapper;
    @Autowired
    private ShopBrandCategoryMapper shopBrandCategoryMapper;
    @Autowired
    private ShopProductCategoryMapper shopProductCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopBrandDto shopBrandDto) {

        // 将dto转换成实体类
        ShopBrand shopBrand = new ShopBrand();
        BeanUtils.copyProperties(shopBrandDto,shopBrand);
        //将品牌存库
        shopBrandMapper.save(shopBrand);
        //将品牌-分类(多)存库
        //判断分类id集合是否为空，不为空就直接入库
        brandCategory(shopBrandDto, shopBrand);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ShopBrandDto shopBrandDto) {
        // 转成实体类
        ShopBrand shopBrand = new ShopBrand();
        BeanUtils.copyProperties(shopBrandDto, shopBrand);
        // 修改
        shopBrandMapper.update(shopBrand);
        // 将品牌-分类删除
        shopBrandCategoryMapper.deleteByBrandId(shopBrand.getId());
        // 将品牌-分类存库
        // 判断分类ID集合是否为空，不为空就入库
        brandCategory(shopBrandDto, shopBrand);
    }

    private void brandCategory(ShopBrandDto shopBrandDto, ShopBrand shopBrand) {
        if (!CollectionUtils.isEmpty(shopBrandDto.getCategoryIds())) {
            List<ShopBrandCategory> brandCategoryList = shopBrandDto.getCategoryIds().stream().map(cid -> {
                ShopBrandCategory shopBrandCategory = new ShopBrandCategory();
                shopBrandCategory.setCategoryId(cid);
                shopBrandCategory.setBrandId(shopBrand.getId());
                return shopBrandCategory;
            }).collect(Collectors.toList());
            // 存库
            shopBrandCategoryMapper.saveBatch(brandCategoryList);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        shopBrandMapper.delete(id);
        shopBrandCategoryMapper.deleteByBrandId(id);
    }

    @Override
    public ShopBrandVo get(Long id) {
        //查询出品牌
        ShopBrand shopBrand = shopBrandMapper.get(id);
        //转换成vo
        ShopBrandVo shopBrandVo=new ShopBrandVo();
        BeanUtils.copyProperties(shopBrand,shopBrandVo);
        //根据品牌id查询品牌-分类关联关系
        List<ShopBrandCategory> shopBrandCategoryList = shopBrandCategoryMapper.getByBrandId(id);
        if (!CollectionUtils.isEmpty(shopBrandCategoryList)){
            //遍历取出分类id
            List<Long> categoryIds = shopBrandCategoryList.stream().map(ShopBrandCategory::getCategoryId)
                    .collect(Collectors.toList());
            //根据关联关系查询分类  根据分类id集合查询所有分类
            List<ShopProductCategory> categoryList = shopProductCategoryMapper.getByIds(categoryIds);
            shopBrandVo.setCategoryList(categoryList);
        }

        return shopBrandVo;
    }

    @Override
    public ShopBrandDto getUpdate(Long id) {

        //根据id查询
        ShopBrand shopBrand = shopBrandMapper.get(id);
        // 转成DTO
        ShopBrandDto shopBrandDto = new ShopBrandDto();
        BeanUtils.copyProperties(shopBrand, shopBrandDto);
        // 查询品牌-分类关系
        List<ShopBrandCategory> brandCategoryList = shopBrandCategoryMapper.getByBrandId(id);
        // 判断集合是否为空，不为空就取出分类ID集合
        if (!CollectionUtils.isEmpty(brandCategoryList)) {
            //遍历取出分类id
            List<Long> categoryIds = brandCategoryList.stream().map(ShopBrandCategory::getCategoryId)
                    .collect(Collectors.toList());
            // set进dto
            shopBrandDto.setCategoryIds(categoryIds);
        }
        return shopBrandDto;
    }

    @Override
    public Page<ShopBrand> getByPage(Page<ShopBrand> page) {
        List<ShopBrand> list = shopBrandMapper.getByPage(page);
        Integer totalCount = shopBrandMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<ShopBrand> getByCategoryId(Long categoryId) {
        //分类和品牌是多对多的关系
        //根据分类id查询关联关系
        List<ShopBrandCategory> brandCategoryList = shopBrandCategoryMapper.getByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(brandCategoryList)){
            return new ArrayList<>(0);
        }
        //取出所有的品牌id
        List<Long> brandIds = brandCategoryList.stream().map(ShopBrandCategory::getCategoryId).collect(Collectors.toList());
         //根据品牌id集合查询
        return shopBrandMapper.getByIds(brandIds);
    }

    @Override
    public List<ShopBrand> getByName(String name) {

        return shopBrandMapper.getByName(name);
    }
}
