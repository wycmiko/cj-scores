package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsWithBLOBs;
import com.cj.shop.api.response.dto.GoodsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsWithBLOBs record);

    GoodsDto  selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsWithBLOBs record);

    int increPv(@Param("id") Long id);

}