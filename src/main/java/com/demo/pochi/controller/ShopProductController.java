package com.demo.pochi.controller;

import com.demo.pochi.common.Page;
import com.demo.pochi.common.Result;
import com.demo.pochi.dto.ShopProductDto;
import com.demo.pochi.pojo.ShopBrand;
import com.demo.pochi.pojo.ShopProduct;
import com.demo.pochi.pojo.vo.ShopProductVo;
import com.demo.pochi.service.ShopBrandService;
import com.demo.pochi.service.ShopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ShopProductController {

    @Autowired
    private ShopProductService shopProductService;

    /**
     * 添加商品
     * @param shopProductDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopProductDto shopProductDto) {
        shopProductService.save(shopProductDto);
        return new Result<>("添加成功");
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/getByPage",method = RequestMethod.POST)
    public Result<Page<ShopProductVo>> getByPage(@RequestBody Page<ShopProductVo> page){
      page =  shopProductService.getByPage(page);
      return new Result<>(page);
    }
    /**
     * 分页查询,没有套装的商品
     * @return
     */
    @RequestMapping(value = "/getByPageHasNotPack",method = RequestMethod.POST)
    public Result<Page<ShopProductVo>> getByPageHasNotPack(@RequestBody Page<ShopProductVo> page){
      page =  shopProductService.getByPageHasNotPack(page);
      return new Result<>(page);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id){
        shopProductService.delete(id);
        return new Result<>("删除成功");
    }


    /**
     * 查询回显数据
     */
    @RequestMapping(value = "getUpdate/{id}",method = RequestMethod.GET)
    public Result<ShopProductVo> getUpdate(@PathVariable Long id){
        ShopProductVo shopProductVo = shopProductService.getUpdate(id);
        return new Result<>(shopProductVo);
    }


    /**
     * 修改
     */
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<?> update(@RequestBody ShopProductDto shopProductDto){
        shopProductService.update(shopProductDto);
        return new Result<>("修改成功");
    }



    /**
     * 上架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.PUT)
    public Result<?> publish(@PathVariable Long id) {
        shopProductService.publish(id);
        return new Result<>("已上架");
    }

    /**
     * 下架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unPublish/{id}", method = RequestMethod.PUT)
    public Result<?> unPublish(@PathVariable Long id) {
        shopProductService.unPublish(id);
        return new Result<>("已下架");
    }

    /**
     * 新品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/news/{id}", method = RequestMethod.PUT)
    public Result<?> news(@PathVariable Long id) {
        shopProductService.news(id);
        return new Result<>("修改成功");
    }

    /**
     * 非新品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/old/{id}", method = RequestMethod.PUT)
    public Result<?> old(@PathVariable Long id) {
        shopProductService.old(id);
        return new Result<>("修改成功");
    }

    /**
     * 推荐
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.PUT)
    public Result<?> recommend(@PathVariable Long id) {
        shopProductService.recommend(id);
        return new Result<>("推荐成功");
    }

    /**
     * 不推荐
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unRecommend/{id}", method = RequestMethod.PUT)
    public Result<?> unRecommend(@PathVariable Long id) {
        shopProductService.unRecommend(id);
        return new Result<>("修改成功");
    }

    /**
     * 查询商品清单
     */
    @RequestMapping(value = "/getProductDetailList/{packCode}",method = RequestMethod.GET)
    public Result<List<ShopProduct>> getProductDetailList(@PathVariable Long packCode){
       List<ShopProduct> productList = shopProductService.getProductDetailList(packCode);
       return new Result<>(productList);

    }


}
