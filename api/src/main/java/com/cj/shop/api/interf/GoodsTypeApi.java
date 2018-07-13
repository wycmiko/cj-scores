package com.cj.shop.api.interf;

import com.cj.shop.api.entity.GoodsType;
import com.cj.shop.api.param.GoodsTypeRequest;
import com.cj.shop.api.response.PagedList;
/**
 * @author yuchuanWeng( )
 * @date 2018/6/22
 * @since 1.0
 */
public interface GoodsTypeApi {
    /**
     * 添加商品分类
     */
    String addGoodsType(GoodsTypeRequest request);

    /**
     * 修改商品分类
     */
    String updateGoodsType(GoodsTypeRequest request);

    /**
     * 查询全部商品分类
     * 三级对应关系
     */
    PagedList<GoodsType> getAllGoodsType(String types, Integer pageNum, Integer pageSize);

    GoodsType getGoodsTypeById(Long typeId, String type);

    /**
     * 删除指定分类
     */
    String deleteGoodsType(Long typeId, Integer type);
}
