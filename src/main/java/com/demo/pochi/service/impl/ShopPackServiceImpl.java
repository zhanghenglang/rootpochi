package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopPackDto;
import com.demo.pochi.mapper.ShopPackMapper;
import com.demo.pochi.mapper.ShopProductMapper;
import com.demo.pochi.mapper.ShopProductPackMapper;
import com.demo.pochi.pojo.ShopPack;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.ShopProductPack;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.ShopPackService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import com.demo.pochi.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopPackServiceImpl implements ShopPackService {


    @Autowired
    private ShopPackMapper shopPackMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopProductMapper shopProductMapper;
    @Autowired
    private ShopProductPackMapper shopProductPackMapper;

    @Override
    public Page<ShopPack> getByPage(Page<ShopPack> page) {
        List<ShopPack> list = shopPackMapper.getByPage(page);
        Integer totalCount = shopPackMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        shopPackMapper.delete(id);
        shopProductPackMapper.deleteByPackCode(id);
    }

    @Override
    public ShopPackDto getById(Long id) {
        ShopPackDto shopPackDto = new ShopPackDto();
        ShopPack shopPack = shopPackMapper.get(id);
        BeanUtils.copyProperties(shopPack,shopPackDto);
        //根据套装id查询商品套装关联信息
        List<ShopProductPack> packList = shopProductPackMapper.getByPackCode(id);

        if(!CollectionUtils.isEmpty(packList)) {
            List<Long> productIds = packList.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
            List<ShopProduct> productList = shopProductMapper.getByIds(productIds);
            shopPackDto.setProductList(productList);
        }
        return shopPackDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ShopPackDto shopPackDto) {
        Long packCode = shopPackDto.getId();
        //拷贝属性
        ShopPack shopPack=new ShopPack();
        BeanUtils.copyProperties(shopPackDto,shopPack);
        //shopPack入库
        int productCount = 0;
        List<ShopProduct> productList = shopPackDto.getProductList();
        if (!CollectionUtils.isEmpty(productList)) {
            productCount = shopPackDto.getProductList().size();
        }
        shopPack.setProductCount(productCount);
        //save
        shopPackMapper.update(shopPack);

        //根据套装id删除商品套装关联信息
        shopProductPackMapper.deleteByPackCode(packCode);

        if (!CollectionUtils.isEmpty(productList)) {
            //保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            //根据所选商品id查询所有商品信息
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
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
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopPackDto shopPackDto) {
        //拷贝属性
        ShopPack shopPack=new ShopPack();
        BeanUtils.copyProperties(shopPackDto,shopPack);
        //获取当前用户登录人
        LoginUser loginUser = ShiroUtils.getLoginUser();
        shopPack.setCreateBy(loginUser.getUsername());
        long packCode = idWorker.nextId();
        shopPack.setId(packCode);
        //shopPack入库
        int productCount = 0;
        List<ShopProduct> productList = shopPackDto.getProductList();
        if (!CollectionUtils.isEmpty(productList)) {
            productCount = shopPackDto.getProductList().size();
        }
        shopPack.setProductCount(productCount);
        //save
        shopPackMapper.save(shopPack);

        if (!CollectionUtils.isEmpty(productList)) {
            //保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            //根据所选商品id查询所有商品信息
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
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
        }

        }


}
