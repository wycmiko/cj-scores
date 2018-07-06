package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsTagMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsTag record);

    GoodsTag selectByPrimaryKey(Long id);

    List<Long> selectIDByTagName(@Param("tagName") String tagName);

    List<Long> selectTagIds(@Param("type") String type);

    int updateByPrimaryKeySelective(GoodsTag record);
}