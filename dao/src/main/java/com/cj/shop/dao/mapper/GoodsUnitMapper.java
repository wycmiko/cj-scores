package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsUnitMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsUnit record);

    GoodsUnit selectByPrimaryKey(Long id);

    List<Long> selectUnitIds(@Param("type") String type);

    int updateByPrimaryKeySelective(GoodsUnit record);
}