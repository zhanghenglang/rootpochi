package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.mapper.ShopProductMapper;
import com.demo.pochi.mapper.ShopProductStatisticMapper;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.ShopProductCollection;
import com.demo.pochi.pojo.ShopProductStatistic;
import com.demo.pochi.pojo.vo.ShopProductCollectionVo;
import com.demo.pochi.repository.ShopProductCollectionRepository;
import com.demo.pochi.service.ShopProductCollectionService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.DateUtils;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopProductCollectionServiceImpl implements ShopProductCollectionService {
    @Autowired
    private ShopProductCollectionRepository shopProductCollectionRepository;
    @Autowired
    private ShopProductMapper shopProductMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ShopProductStatisticMapper shopProductStatisticMapper;


    @Override
    public void changeCollection(ShopProductCollection shopProductCollection) {
        // 先查询，如果存在，就直接删除，如果不存在，就添加
        LoginUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        ShopProductCollection collection = shopProductCollectionRepository.getByProductIdAndCreateBy(shopProductCollection.getProductId(), username);
        if (collection != null) {
            shopProductCollectionRepository.deleteById(collection.getId());
            shopProductStatisticMapper.removeCollectionCount(collection.getProductId());
        } else {
            ShopProduct product = shopProductMapper.getInfoById(shopProductCollection.getProductId());
            collection = new ShopProductCollection();
            collection.setId(idWorker.nextId());
            collection.setProductId(shopProductCollection.getProductId());
            collection.setCreateBy(username);
            collection.setCreateTime(DateUtils.newDateTime());
            collection.setProductPic(product.getPic());
            collection.setProductName(product.getName());
            collection.setProductBrand(product.getBrandName());
            collection.setProductPrice(product.getPrice());
            collection.setProductCategoryId(product.getCategoryId());
            shopProductCollectionRepository.save(collection);
            shopProductStatisticMapper.addCollectionCount(collection.getProductId());
        }
    }

    @Override
    public ShopProductCollection getByProductId(Long productId) {
        return shopProductCollectionRepository.getByProductIdAndCreateBy(productId, ShiroUtils.getLoginUser().getUsername());
    }

    @Override
    public Page<ShopProductCollectionVo> getByPage(Page<ShopProductCollection> page) {
        // 构造一个查询对象
        Query query = new Query();
        // 设置参数
        LoginUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        query.addCriteria(Criteria.where("create_by").is(username));
        // 计算分页参数
        Integer pageNumber = page.getPageNumber();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            page.setPageNumber(pageNumber);
        }
        Integer pageSize = page.getPageSize();
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
            page.setPageSize(pageSize);
        }
        // 设置跳过多少条
        query.skip((pageNumber - 1) * pageSize);
        // 取出多少条
        query.limit(pageSize);
        // 排序对象
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "create_time");
        // 设置排序对象
        query.with(Sort.by(order));
        List<ShopProductCollection> list = mongoTemplate.find(query, ShopProductCollection.class);
        long count = mongoTemplate.count(query, ShopProductCollection.class);
        // 处理list
        List<Long> productIds = list.stream().map(ShopProductCollection::getProductId).collect(Collectors.toList());
        List<ShopProductCollectionVo> voList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productIds)) {
            List<ShopProductStatistic> statisticList = shopProductStatisticMapper.getByProductIds(productIds);
            voList = list.stream().map(e -> {
                ShopProductCollectionVo vo = new ShopProductCollectionVo();
                BeanUtils.copyProperties(e, vo);
                // 匹配到该商品对应的收藏信息
                ShopProductStatistic statistic = statisticList.stream().filter(s -> s.getProductId().equals(e.getProductId())).findFirst().orElse(new ShopProductStatistic());
                vo.setCollectionCount(statistic.getCollectionCount());
                return vo;
            }).collect(Collectors.toList());
        }
        Page<ShopProductCollectionVo> resultPage = new Page<>();
        resultPage.setPageSize(page.getPageSize());
        resultPage.setPageNumber(page.getPageNumber());
        resultPage.setList(voList);
        resultPage.setTotalCount((int) count);
        return resultPage;
    }
}
