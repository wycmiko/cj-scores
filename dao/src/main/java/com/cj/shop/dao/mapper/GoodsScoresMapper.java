package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsScores;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsScoresMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsScores record);

    int insertSelective(GoodsScores record);

    GoodsScores selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsScores record);

}