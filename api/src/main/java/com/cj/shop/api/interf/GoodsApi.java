package com.cj.shop.api.interf;

import com.cj.shop.api.entity.GoodsBrand;
import com.cj.shop.api.entity.GoodsSupply;
import com.cj.shop.api.param.GoodsBrandRequest;
import com.cj.shop.api.param.GoodsRequest;
import com.cj.shop.api.param.GoodsSupplyRequest;
import com.cj.shop.api.param.select.GoodsSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.GoodsDto;

/**
 * 商品相关服务 API
 *
 * @author yuchuanWeng( )
 * @date 2018/6/22
 * @since 1.0
 */
public interface GoodsApi {

    /**
     * 供应商管理
     */
    /**
     * 添加供应商
     *
     * @param request
     * @return
     */
    String insertSupply(GoodsSupplyRequest request);

    /**
     * 修改供应商(包含禁用)
     *
     * @param request
     * @return
     */
    String updateSupply(GoodsSupplyRequest request);

    /**
     * 查询全部供应商
     *
     * @return
     */
    PagedList<GoodsSupply> findAllSupplies(String supplyName, Integer pageNum, Integer pageSize, String type);

    /**
     * 查询供应商明细
     *
     * @return
     */
    GoodsSupply getSupplyDetail(Long supplyId);

    /**
     * 品牌管理
     */
    /**
     * 添加品牌
     *
     * @param request
     * @return
     */
    String insertBrand(GoodsBrandRequest request);

    /**
     * 修改品牌
     *
     * @param request
     * @return
     */
    String updateBrand(GoodsBrandRequest request);

    /**
     * 查询品牌详情
     *
     * @param brandId
     * @return
     */
    GoodsBrand getBrandDetail(Long brandId);

    /**
     * 查询全部品牌
     *
     * @param brandName
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    PagedList<GoodsBrand> findAllBrands(String brandName, Integer pageNum, Integer pageSize, String type);


    /**
     * 添加商品
     */
    String insertGood(GoodsRequest request);

    /**
     * 修改商品
     *
     * @param request
     * @return
     */
    String updateGood(GoodsRequest request);

    /**
     * 复合条件查询全部商品
     */
    PagedList<GoodsDto> getAllGoods(GoodsSelect select);

    /**
     * 查询单条商品明细
     */
    GoodsDto getGoodsDetail(Long goodsId);


}
