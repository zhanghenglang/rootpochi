package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.dto.ShopCouponDto;
import com.demo.pochi.enums.StateEnums;
import com.demo.pochi.mapper.*;
import com.demo.pochi.pojo.ShopCoupon;
import com.demo.pochi.pojo.ShopCouponCategory;
import com.demo.pochi.pojo.ShopCouponProduct;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.vo.SysUserVo;
import com.demo.pochi.service.ShopCouponService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopCouponServiceImpl implements ShopCouponService {

    @Autowired
    private ShopCouponMapper shopCouponMapper;
    @Autowired
    private ShopCouponCategoryMapper shopCouponCategoryMapper;
    @Autowired
    private ShopCouponProductMapper shopCouponProductMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopProductMapper shopProductMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopCouponDto shopCouponDto) {
        long couponId = idWorker.nextId();
        // 复制属性
        ShopCoupon shopCoupon = new ShopCoupon();
        BeanUtils.copyProperties(shopCouponDto, shopCoupon);
        // 设置默认值
        LoginUser loginUser = ShiroUtils.getLoginUser();
        shopCoupon.setId(couponId);
        shopCoupon.setCreateBy(loginUser.getUsername());
        shopCoupon.setUpdateBy(loginUser.getUsername());
        //设置剩余数量为发行数量
        shopCoupon.setRestCount(shopCoupon.getPublishCount());
        shopCouponMapper.save(shopCoupon);
        // 如果有分类，添加优惠券-分类关联
        if (StateEnums.CATEGORY.getCode().equals(shopCoupon.getCouponType())) {
            List<ShopCouponCategory> couponCategoryList = shopCouponDto.getCategoryList().stream().map(e -> new ShopCouponCategory(couponId, e.getId())).collect(Collectors.toList());
            shopCouponCategoryMapper.saveBatch(couponCategoryList);
        }
        // 如果有商品，添加优惠券-商品关联
        if (StateEnums.PRODUCT.getCode().equals(shopCoupon.getCouponType())) {
            List<ShopCouponProduct> couponProductList = shopCouponDto.getProductList().stream().map(e -> new ShopCouponProduct(couponId, e.getId())).collect(Collectors.toList());
            shopCouponProductMapper.saveBatch(couponProductList);
        }
    }

    @Override
    public void delete(Long id) {
        shopCouponMapper.delete(id);
    }

    @Override
    public void down(Long id) {
        shopCouponMapper.down(id);
    }

    @Override
    public Page<ShopCoupon> getByPage(Page<ShopCoupon> page) {
        List<ShopCoupon> list = shopCouponMapper.getByPage(page);
        Integer totalCount = shopCouponMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<ShopCoupon> getProductCoupon(Long productId) {
        // 查询商品信息
        ShopProduct product = shopProductMapper.getInfoById(productId);
        // 查询全场通用优惠券
        List<ShopCoupon> bothList = shopCouponMapper.getBothCoupon();
        // 查询该商品所在分类的优惠券
        List<ShopCoupon> categoryCouponList = shopCouponMapper.getByCategoryId(product.getCategoryId());
        // 查询该商品的优惠券
        List<ShopCoupon> productCouponList = shopCouponMapper.getByProductId(productId);
        bothList.addAll(categoryCouponList);
        bothList.addAll(productCouponList);
        return bothList;
    }
}
