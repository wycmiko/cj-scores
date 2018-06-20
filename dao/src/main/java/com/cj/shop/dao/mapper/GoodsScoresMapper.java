package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsScores;

public interface GoodsScoresMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsScores record);

    int insertSelective(GoodsScores record);

    GoodsScores selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsScores record);

}