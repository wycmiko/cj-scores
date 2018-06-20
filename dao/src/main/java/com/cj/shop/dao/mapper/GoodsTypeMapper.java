package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsType record);

}