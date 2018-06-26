package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsSpecWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GoodsSpecMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsSpecWithBLOBs record);

    GoodsSpecWithBLOBs selectByPrimaryKey(Long id);

    List<Long> selectAllSpecIds(@Param("type") String type);

    int updateByPrimaryKeySelective(GoodsSpecWithBLOBs record);
}