package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsStockMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(Long id);

    List<GoodsStock> selectByGoodsSn(@Param("goodsSn") String goodsSn);

    int updateByPrimaryKeySelective(GoodsStock record);
}