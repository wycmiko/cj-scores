package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsUnit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsUnitMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsUnit record);

    GoodsUnit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsUnit record);
}