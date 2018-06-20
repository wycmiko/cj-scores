package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsType;

public interface GoodsTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsType record);

}