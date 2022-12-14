package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopPackDto;
import com.demo.pochi.mapper.ShopPackMapper;
import com.demo.pochi.mapper.ShopProductMapper;
import com.demo.pochi.mapper.ShopProductPackMapper;
import com.demo.pochi.pojo.ShopPack;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.ShopProductPack;
import com.demo.pochi.pojo.vo.ShopProductPackVo;
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

import java.util.ArrayList;
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
        //????????????id??????????????????????????????
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
        //????????????
        ShopPack shopPack=new ShopPack();
        BeanUtils.copyProperties(shopPackDto,shopPack);
        //shopPack??????
        int productCount = 0;
        List<ShopProduct> productList = shopPackDto.getProductList();
        if (!CollectionUtils.isEmpty(productList)) {
            productCount = shopPackDto.getProductList().size();
        }
        shopPack.setProductCount(productCount);
        //save
        shopPackMapper.update(shopPack);

        //????????????id??????????????????????????????
        shopProductPackMapper.deleteByPackCode(packCode);

        if (!CollectionUtils.isEmpty(productList)) {
            //???????????????????????????
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            //??????????????????id????????????????????????
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
        //????????????
        ShopPack shopPack=new ShopPack();
        BeanUtils.copyProperties(shopPackDto,shopPack);
        //???????????????????????????
        LoginUser loginUser = ShiroUtils.getLoginUser();
        shopPack.setCreateBy(loginUser.getUsername());
        long packCode = idWorker.nextId();
        shopPack.setId(packCode);
        //shopPack??????
        int productCount = 0;
        List<ShopProduct> productList = shopPackDto.getProductList();
        if (!CollectionUtils.isEmpty(productList)) {
            productCount = shopPackDto.getProductList().size();
        }
        shopPack.setProductCount(productCount);
        //save
        shopPackMapper.save(shopPack);

        if (!CollectionUtils.isEmpty(productList)) {
            //???????????????????????????
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            //??????????????????id????????????????????????
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
    public List<ShopProductPackVo> getByProductId(Long productId) {
        //????????????????????????????????????
        ShopProduct shopProduct = shopProductMapper.getInfoById(productId);
        Long packCode = shopProduct.getPackCode();
        //????????????????????????????????????????????????;?????????????????????????????????????????????????????????????????????????????????
        if (packCode == null){
            List<ShopProductPackVo> resultList = new ArrayList<>(1);
            //??????????????????????????????????????????????????????????????????????????????
            ShopProductPackVo packVo = new ShopProductPackVo();
            packVo.setProductId(productId);
            packVo.setPic(shopProduct.getPic());
            packVo.setPackCode(shopProduct.getPackCode());
            packVo.setPrice(shopProduct.getPrice());
            packVo.setProductName(shopProduct.getName());
            packVo.setSpecName(shopProduct.getSpecs());
            packVo.setStock(shopProduct.getStock());
            if (StringUtils.isBlank(packVo.getSpecName())) {
                packVo.setSpecName(packVo.getProductName());
            }
            resultList.add(packVo);
            return resultList;
        }
        //???????????????????????????
        //????????????????????????????????????
        List<ShopProductPack> productPackList = shopProductPackMapper.getByPackCode(packCode);
        //????????????????????????
        List<Long> productIds = productPackList.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
        //??????????????????
        List<ShopProduct> shopProductList = shopProductMapper.getByIds(productIds);
        //?????????Vo
        List<ShopProductPackVo> list = shopProductList.stream().map(e -> {
            ShopProductPackVo packVo = new ShopProductPackVo();
            packVo.setProductId(productId);
            packVo.setPic(e.getPic());
            packVo.setPackCode(e.getPackCode());
            packVo.setPrice(e.getPrice());
            packVo.setProductName(e.getName());
            packVo.setSpecName(e.getSpecs());
            packVo.setStock(e.getStock());
            if (StringUtils.isBlank(packVo.getSpecName())) {
                packVo.setSpecName(packVo.getProductName());
            }
            return packVo;
        }).collect(Collectors.toList());

        return list;
    }


}
