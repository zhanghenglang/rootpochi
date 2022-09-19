package com.demo.pochi.service.impl;

import com.demo.pochi.common.Page;
import com.demo.pochi.mapper.ShopProductMapper;
import com.demo.pochi.mapper.ShopProductStatisticMapper;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.ShopProductHistory;
import com.demo.pochi.pojo.ShopProductStatistic;
import com.demo.pochi.repository.ShopProductRepository;
import com.demo.pochi.service.ShopProductHistoryService;
import com.demo.pochi.shiro.LoginUser;
import com.demo.pochi.utils.DateUtils;
import com.demo.pochi.utils.IdWorker;
import com.demo.pochi.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopProductHistoryServiceImpl implements ShopProductHistoryService {
    @Autowired
    private ShopProductStatisticMapper shopProductStatisticMapper;
    @Autowired
    private ShopProductRepository shopProductRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ShopProductMapper shopProductMapper;

    @Override
    public void save(ShopProductHistory shopProductHistory) {
        try {
            shopProductHistory.setId(idWorker.nextId());
            ShopProduct product = shopProductMapper.getInfoById(shopProductHistory.getProductId());
            shopProductHistory.setProductBrand(product.getBrandName());
            shopProductHistory.setProductCategoryId(product.getCategoryId());
            shopProductHistory.setProductName(product.getName());
            shopProductHistory.setProductPic(product.getPic());
            shopProductHistory.setProductPrice(product.getPrice());
            LoginUser loginUser = ShiroUtils.getLoginUser();
            shopProductHistory.setCreateBy(loginUser.getUsername());
            shopProductHistory.setCreateDay(DateUtils.newDate());
            shopProductHistory.setCreateTime(DateUtils.newDateTime());
            shopProductRepository.save(shopProductHistory);

            ShopProductStatistic statistic = shopProductStatisticMapper.getByProductId(product.getId());
            if (statistic == null) {
                shopProductStatisticMapper.saveByProductId(product.getId());
            } else {
                shopProductStatisticMapper.updateHistory(statistic.getId());
            }
        }catch (Exception e) {
            log.error("保存历史记录异常：", e);
        }

    }

    @Override
    public void clearHistory() {
        Query query = new Query();
        query.addCriteria(Criteria.where("create_by").is(ShiroUtils.getLoginUser().getUsername()));
        mongoTemplate.remove(query, ShopProductHistory.class);
    }

    @Override
    public Map<String, List<ShopProductHistory>> getByPage(Page<ShopProductHistory> page) {
        // 构造查询对象
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
        // 构造排序对象
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "create_time");
        // 设置排序对象
        query.with(Sort.by(order));
        // 查询
        List<ShopProductHistory> list = mongoTemplate.find(query, ShopProductHistory.class);
        Map<String, List<ShopProductHistory>> dataMap = list.stream().collect(Collectors.groupingBy(ShopProductHistory::getCreateDay));
        return dataMap;
    }
}
