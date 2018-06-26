package com.cj.shop.api.interf;

import com.cj.shop.api.entity.GoodsSpecWithBLOBs;
import com.cj.shop.api.entity.GoodsTag;
import com.cj.shop.api.param.GoodsSpecRequest;
import com.cj.shop.api.param.GoodsTagRequest;
import com.cj.shop.api.response.PagedList;

/**
 * 商品扩展api接口
 */
public interface GoodsExtensionApi {
    /**
     * 添加商品规格
     * @param request
     * @return
     */
    String insertGoodsSpec(GoodsSpecRequest request);

    /**
     * 修改商品规格
     */
    String updateGoodsSpec(GoodsSpecRequest request);

    /**
     * 查询规格详情
     */
    GoodsSpecWithBLOBs getGoodsSpecDetail(Long specId);

    /**
     * 查询全部规格
     * @param pageNum
     * @param pageSize
     * @param type all 显示全部(包含禁用) exist(显示)
     * @return
     */
    PagedList<GoodsSpecWithBLOBs> findAllSpecs(Integer pageNum, Integer pageSize, String type);

    /**
     * 添加商品标签
     * @param request
     * @return
     */
    String insertGoodsTag(GoodsTagRequest request);

    /**
     * 修改商品标签
     */
    String updateGoodsTag(GoodsTagRequest request);

    /**
     * 查询标签详情
     */
    GoodsTag getGoodsTagDetail(Long tagId);

    /**
     * 查询全部标签
     * @param pageNum
     * @param pageSize
     * @param type all 显示全部(包含禁用) exist(显示未被删除的)
     * @return
     */
    PagedList<GoodsTag> findAllTags(Integer pageNum, Integer pageSize, String type);
}
