package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.constant.CoreConstant;
import com.demo.pochi.dto.ShopProductDto;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.mapper.*;
import com.demo.pochi.pojo.*;
import com.demo.pochi.pojo.vo.ShopProductVo;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.ShopProductService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import com.demo.pochi.utils.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ShopProductServiceImpl implements ShopProductService {


    @Autowired
    private ShopProductMapper shopProductMapper;

    @Autowired
    private ShopBrandMapper shopBrandMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopProductCategoryMapper shopProductCategoryMapper;
    @Autowired
    private ShopProductPackMapper shopProductPackMapper;
    @Autowired
    private ShopPackMapper shopPackMapper;


    @Override
    public void save(ShopProductDto shopProductDto) {
        //转换成实体类
        ShopProduct shopProduct = new ShopProduct();
        BeanUtils.copyProperties(shopProductDto,shopProduct);
        //处理轮播图,保证至少都有一张图片
        if(CollectionUtils.isEmpty(shopProductDto.getAlbumPicList())){
            //转换成一个字符串，并且用","拼接
            shopProduct.setAlbumPics(StringUtils.join(shopProductDto.getAlbumPicList(),","));
        }else {
            //如果为空则用商品图片代替
            shopProduct.setAlbumPics(shopProduct.getPic());
        }
          /*
            品牌名称
            一般来讲，任何场景下我们都不能信任前端所传递的参数
            我们只能接收前端传递的品牌ID，名称我们自己去表里查
            如果我们系统的安全性做的比较足的话
            那么前端参数被伪造的概率是相当低的
            这个时候我们就可以相信前端传的参数
            我们本次课程系统安全性较差，因此不相信前端的参数
         */
        ShopBrand shopBrand = shopBrandMapper.get(shopProduct.getBrandId());
        shopProduct.setBrandName(shopBrand.getName());
        //如果副标题为空就把标题给他
        if (StringUtils.isBlank(shopProduct.getSubTitle())){
            shopProduct.setSubTitle(shopProduct.getName());
        }

        //创建人、修改人
        LoginUser loginUser = ShiroUtils.getLoginUser();
        shopProduct.setCreateBy(loginUser.getUsername());
        shopProduct.setUpdateBy(loginUser.getUsername());
        shopProduct.setId(idWorker.nextId());
        shopProductMapper.save(shopProduct);

        //处理关联套装逻辑
        handleSaveProductPack(shopProductDto, shopProduct, loginUser);

    }

    /**
     * 处理添加商品时的套装逻辑
     * @param shopProductDto 待添加商品
     * @param shopProduct 当前商品
     * @param loginUser 当前用户
     */
    private void handleSaveProductPack(ShopProductDto shopProductDto, ShopProduct shopProduct, LoginUser loginUser) {
        ShopPack shopPack = shopProductDto.getShopPack();
        List<ShopProduct> productList = shopProductDto.getProductList();

        //- 用户选择了关联套装和关联商品
        if (shopPack !=null && !CollectionUtils.isEmpty(productList)){
            long packCode =shopPack.getId();
            //  - 该商品没有加入其它套装：将该商品和选择的商品都加入到套装中。
            //保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());

            //根据所选商品id查询所有商品信息
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            //将该商品加入到productIds
            products.add(shopProduct);
            handleProductPack(packCode,products);
        }else if (shopPack !=null){
            long packCode =shopPack.getId();
            //- 用户没同时选择关联套装和关联商品
            //  - 用户选择了关联套装
            //    - 该商品没套装：将该商品加入到选择的套装中
            List<Long> productIds = new ArrayList<>();
            //根据所选商品id查询所有商品信息
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            //将该商品加入到productIds
            products.add(shopProduct);
            handleProductPack(packCode,products);

        }else if (!CollectionUtils.isEmpty(productList)){
            //  - 用户选择了关联商品
            //    - 该商品没套装：将该商品和选择的商品打包成一个套装
            //拷贝属性
             shopPack=new ShopPack();
            //入库
            shopPack.setCreateBy(loginUser.getUsername());
            shopPack.setName(shopProduct.getName());
            shopPack.setProductCount(productList.size()+1);
            long packCode = idWorker.nextId();
            shopPack.setId(packCode);
            //save
            shopPackMapper.save(shopPack);
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());

            //根据所选商品id查询所有商品信息
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            //将该商品加入到products
            products.add(shopProduct);
            handleProductPack(packCode,products);

        }
    }

    /**
     * 处理添加商品是的套装逻辑
     * @param packCode
     * @param products
     * @return
     */
    private void handleProductPack(long packCode, List<ShopProduct> products) {
        List<ShopProductPack> packList = products.stream().map(e -> {
            ShopProductPack shopProductPack = new ShopProductPack();
            shopProductPack.setProductId(e.getId());
            shopProductPack.setPackCode(packCode);
            shopProductPack.setPrice(e.getPrice());
            shopProductPack.setStock(e.getStock());
            shopProductPack.setLowStock(e.getLowStock());
            shopProductPack.setSpecName(e.getSpecs());
            if (StringUtils.isBlank(e.getSpecs())){
                shopProductPack.setSpecName(e.getName());
            }
            shopProductPack.setProductName(e.getName());
            return shopProductPack;
        }).collect(Collectors.toList());
        shopProductPackMapper.saveBatch(packList);
        shopPackMapper.updateProductCount(packCode,packList.size());
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<ShopProductVo> getByPage(Page<ShopProductVo> page) {
        //查询list和总条数
        List<ShopProduct> list = shopProductMapper.getByPage(page);
        Integer  totalCount=shopProductMapper.countByPage(page);
        //ShopProduct的list转换成vo的list
        List<ShopProductVo> shopProductVoList = list.stream().map(e -> {
            ShopProductVo shopProductVo = new ShopProductVo();
            BeanUtils.copyProperties(e, shopProductVo);
            //轮播图处理
            String albumPics = e.getAlbumPics();
            if (StringUtils.isNotBlank(albumPics)) {
                shopProductVo.setAlbumPicList(Arrays.asList(albumPics.split(",")));
            }
            return shopProductVo;

        }).collect(Collectors.toList());
        //处理分类，根据分类id集合查询名称
        List<Long> categoryIds = shopProductVoList.stream().map(ShopProductVo::getCategoryId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(categoryIds)){
            List<ShopProductCategory> categoryList = shopProductCategoryMapper.getByIds(categoryIds);
            //封装
            shopProductVoList.forEach(e -> {
                ShopProductCategory category = categoryList.stream().filter(c -> c.getId().equals(e.getCategoryId())).findFirst().orElse(new ShopProductCategory());
                e.setCategory(category);
            });
        }

        page.setList(shopProductVoList);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public ShopProductVo getUpdate(Long id) {

        // 根据id查询
        ShopProduct product = shopProductMapper.getById(id);
        // 转换成VO
        ShopProductVo shopProductVo = new ShopProductVo();
        BeanUtils.copyProperties(product, shopProductVo);
        // 处理轮播图
        if (StringUtils.isNotBlank(product.getAlbumPics())) {
            shopProductVo.setAlbumPicList(Arrays.asList(product.getAlbumPics().split(",")));
        }
        // 处理categoryIds
        // 拿到子节点id
        Long categoryId = product.getCategoryId();
        Deque<Long> deque = new ArrayDeque<>(4);
        while (categoryId != null && !categoryId.equals(CoreConstant.DEFAULT_PARENT_ID)) {
            // 根据子节点ID查询分类
            ShopProductCategory category = shopProductCategoryMapper.get(categoryId);
            if (category != null) {
                // 入列
                deque.push(category.getId());
                categoryId = category.getParentId();
            }else {
                break;
            }
        }
        //
        // 封装到vo里
        shopProductVo.setCategoryIds(new ArrayList<>(deque));
        //查询商品查询商品所在的关联
        ShopProductPack productPack = shopProductPackMapper.getByProductId(shopProductVo.getId());
        if (productPack != null){
            //根据取出来的套装编号查询套装
            ShopPack shopPack = shopPackMapper.get(productPack.getPackCode());
            shopProductVo.setShopPack(shopPack);
            //根据套装编号查询商品关联
            List<ShopProductPack> productPackList = shopProductPackMapper.getByPackCode(productPack.getPackCode());
            //获取商品id集合
            if (!CollectionUtils.isEmpty(productPackList)){
                List<Long> list = productPackList.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
                //根据商品ID集合查询商品
                List<ShopProduct> productList = shopProductMapper.getByIds(list);
                shopProductVo.setProductList(productList);
            }
        }
        return shopProductVo;
    }

    @Override
    public void update(ShopProductDto shopProductDto) {
        ShopProduct shopProduct = new ShopProduct();
        BeanUtils.copyProperties(shopProductDto, shopProduct);
        // 处理轮播图
        List<String> albumPicList = shopProductDto.getAlbumPicList();
        if(CollectionUtils.isEmpty(albumPicList)) {
            shopProduct.setAlbumPics(shopProduct.getPic());
        }else {
            shopProduct.setAlbumPics(StringUtils.join(albumPicList, ","));
        }
        ShopBrand brand = shopBrandMapper.get(shopProduct.getBrandId());
        shopProduct.setBrandName(brand.getName());
        // 副标题如果为空，就设置为标题
        if (StringUtils.isBlank(shopProduct.getSubTitle())) {
            shopProduct.setSubTitle(shopProduct.getName());
        }
        // 修改人
        LoginUser loginUser = ShiroUtils.getLoginUser();
        shopProduct.setUpdateBy(loginUser.getUsername());
        shopProductMapper.update(shopProduct);
        // 处理商品套装
        handleUpdateProductPack(shopProductDto, shopProduct, loginUser);
    }

    /**
     * 处理修改时的商品套装逻辑
     *
     * @param shopProductDto 前端传参的商品对象
     * @param shopProduct    当前商品
     * @param loginUser      当前登录用户
     */
    private void handleUpdateProductPack(ShopProductDto shopProductDto, ShopProduct shopProduct, LoginUser loginUser) {
        ShopPack shopPack = shopProductDto.getShopPack();
        List<ShopProduct> productList = shopProductDto.getProductList();
        // * 用户选择了关联套装和关联商品
        if (shopPack != null && !CollectionUtils.isEmpty(productList)) {
            // 根据商品编号查询套装
            //  * 该商品没有加入其它套装：将该商品和选择的商品都加入到套装中。
            //这里可能没有我们选择的商品
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            // 将该商品加入到products
            products.add(shopProduct);
            handleUpdateProductPack(shopProductDto, shopPack, products);
        } else if (shopPack != null) {
            //* 用户没同时选择关联套装和关联商品
            //  * 用户选择了关联套装
            //    * 该商品没套装：将该商品加入到选择的套装中
            List<ShopProduct> products = new ArrayList<>();
            // 将该商品加入到products
            products.add(shopProduct);
            handleUpdateProductPack(shopProductDto, shopPack, products);
        } else if (!CollectionUtils.isEmpty(productList)) {
            //  * 用户选择了关联商品
            //    * 该商品没套装：将该商品和选择的商品打包成一个套装
            List<ShopProduct> products = new ArrayList<>();
            // 将该商品加入到products
            products.add(shopProduct);
            // 创建套装
            shopPack = new ShopPack();
            // ShopPack入库
            shopPack.setCreateBy(loginUser.getUsername());
            shopPack.setName(shopProduct.getName());
            shopPack.setProductCount(productList.size() + 1);
            long packCode = idWorker.nextId();
            shopPack.setId(packCode);
            // 保存
            shopPackMapper.save(shopPack);
            handleUpdateProductPack(shopProductDto, shopPack, products);
        }
    }


    /**
     * 处理修改商品时的套装逻辑
     *
     * @param shopProductDto
     * @param shopPack
     * @param products
     */
    private void handleUpdateProductPack(ShopProductDto shopProductDto, ShopPack shopPack, List<ShopProduct> products) {
        //这里应该查询出products所有商品所在的关联关系，并更新商品套装
        List<Long> productIds = products.stream().map(ShopProduct::getId).collect(Collectors.toList());
        List<ShopProductPack> productPackList = shopProductPackMapper.getByProductIds(productIds);

        if (!CollectionUtils.isEmpty(productPackList)){
            productPackList.forEach(e->{
                Long oldPackCode = e.getPackCode();
                ShopPack oldPack = shopPackMapper.get(oldPackCode);
                oldPack.setProductCount(oldPack.getProductCount() - 1);
                // 如果商品数等于0，就删除该套装，否则修改
                if (oldPack.getProductCount() == 0) {
                    shopPackMapper.delete(oldPackCode);
                } else {
                    shopPackMapper.update(oldPack);
                }
            });
        }

        //删除旧的关联关系
        shopProductPackMapper.deleteByProductIds(productIds);
        handleProductPack(shopPack.getId(),products);



    }



    @Override
    public void delete(Long id) {
        shopProductMapper.delete(id);
    }

    @Override
    public void publish(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setPublishStatus(StateEnums.ENABLED.getCode());
        shopProductMapper.updatePublish(product);
    }

    @Override
    public void unPublish(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setPublishStatus(StateEnums.NOT_ENABLE.getCode());
        shopProductMapper.updatePublish(product);
    }

    @Override
    public void news(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setNewStatus(StateEnums.ENABLED.getCode());
        shopProductMapper.updateNewStatus(product);
    }

    @Override
    public void old(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setNewStatus(StateEnums.NOT_ENABLE.getCode());
        shopProductMapper.updateNewStatus(product);
    }

    @Override
    public void recommend(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setRecommendStatus(StateEnums.ENABLED.getCode());
        shopProductMapper.updateRecommend(product);
    }

    @Override
    public void unRecommend(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setRecommendStatus(StateEnums.NOT_ENABLE.getCode());
        shopProductMapper.updateRecommend(product);
    }

    @Override
    public List<ShopProduct> getProductDetailList(Long packCode) {

        List<ShopProductPack> byPackCode = shopProductPackMapper.getByPackCode(packCode);

        if (CollectionUtils.isEmpty(byPackCode)){
            return new ArrayList<>(0);
        }
        //获取商品id
        List<Long> productIds = byPackCode.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
        List<ShopProduct> productList = shopProductMapper.getByIds(productIds);
        return  productList;
    }

    @Override
    public Page<ShopProductVo> getByPageHasNotPack(Page<ShopProductVo> page) {
        //查询list和总条数
        List<ShopProduct> list = shopProductMapper.getByPageHasNotPack(page);
        Integer  totalCount=shopProductMapper.countByPageHasNotPack(page);
        //ShopProduct的list转换成vo的list
        List<ShopProductVo> shopProductVoList = list.stream().map(e -> {
            ShopProductVo shopProductVo = new ShopProductVo();
            BeanUtils.copyProperties(e, shopProductVo);
            return shopProductVo;

        }).collect(Collectors.toList());
        //处理分类，根据分类id集合查询名称
        List<Long> categoryIds = shopProductVoList.stream().map(ShopProductVo::getCategoryId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(categoryIds)){
            List<ShopProductCategory> categoryList = shopProductCategoryMapper.getByIds(categoryIds);
            //封装
            shopProductVoList.forEach(e -> {
                ShopProductCategory category = categoryList.stream().filter(c -> c.getId().equals(e.getCategoryId())).findFirst().orElse(new ShopProductCategory());
                e.setCategory(category);
            });
        }

        page.setList(shopProductVoList);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<ShopProduct> getNewProduct() {
        return shopProductMapper.getNewProduct();
    }

    @Override
    public List<ShopProduct> getRecommendList() {
        return shopProductMapper.getRecommendList();
    }

    @Override
    public ShopProductVo get(Long id) {

        // 根据id查询
        ShopProduct product = shopProductMapper.getById(id);
        // 转换成VO
        ShopProductVo shopProductVo = new ShopProductVo();
        BeanUtils.copyProperties(product, shopProductVo);
        // 处理轮播图
        if (StringUtils.isNotBlank(product.getAlbumPics())) {
            shopProductVo.setAlbumPicList(Arrays.asList(product.getAlbumPics().split(",")));
        }
        return shopProductVo;
    }

    @Override
    public List<ShopProduct> getRankByProduct(Long productId) {
        // 查询商品
        ShopProduct product = shopProductMapper.getInfoById(productId);
        //取出分类id
        Long categoryId = product.getCategoryId();
        //查询分类下，销量最高的6个商品
        return shopProductMapper.getRankByCategory(categoryId);
    }
}
