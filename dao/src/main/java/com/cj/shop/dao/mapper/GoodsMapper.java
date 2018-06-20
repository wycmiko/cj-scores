package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsWithBLOBs record);

    int insertSelective(GoodsWithBLOBs record);

    GoodsWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsWithBLOBs record);

}