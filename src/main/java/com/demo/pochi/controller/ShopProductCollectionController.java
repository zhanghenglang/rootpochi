package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.pojo.ShopProductCollection;
import com.demo.pochi.pojo.vo.ShopProductCollectionVo;
import com.demo.pochi.service.ShopProductCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *uni-app收藏
 */
@RestController
@RequestMapping("/collection")
public class ShopProductCollectionController {

    @Autowired
    private ShopProductCollectionService shopProductCollectionService;
    /**
     * 切换收藏状态
     *
     * @param shopProductCollection
     * @return
     */
    @RequestMapping(value = "/changeCollection", method = RequestMethod.POST)
    public Result<?> changeCollection(@RequestBody ShopProductCollection shopProductCollection) {
        shopProductCollectionService.changeCollection(shopProductCollection);
        return new Result<>("操作成功！");
    }

    /**
     * 根据商品ID查询
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getByProductId/{productId}", method = RequestMethod.GET)
    public Result<ShopProductCollection> getByProductId(@PathVariable Long productId) {
        ShopProductCollection shopProductCollection = shopProductCollectionService.getByProductId(productId);
        return new Result<>(shopProductCollection);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<ShopProductCollectionVo>> getByPage(@RequestBody Page<ShopProductCollection> page) {
        Page<ShopProductCollectionVo> resultPage = shopProductCollectionService.getByPage(page);
        return new Result<>(resultPage);
    }

}
