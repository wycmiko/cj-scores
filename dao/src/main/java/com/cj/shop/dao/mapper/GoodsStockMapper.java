package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsStock;
import com.cj.shop.api.param.StockSelectRequest;
import com.cj.shop.api.response.dto.GoodsStockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsStockMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(Long id);

    List<Long> selectByGoodsTypeIds(StockSelectRequest request);

    GoodsStockDto selectByGoodsType(@Param("id") Long id);

    int updateByPrimaryKeySelective(GoodsStock record);

    /**
     * 统计某商品总的库存量
     */
    Long getTotalStockNum(@Param("goodSn")String goodsSn);
}