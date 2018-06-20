package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsVisit;

public interface GoodsVisitMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsVisit record);

    GoodsVisit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsVisit record);

}