package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsTypeMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("type") Integer type);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(@Param("id") Long id, @Param("type") String type);

    List<GoodsType> selectAllTopsByParam(@Param("parentNullFlag") Integer parentNullFlag,
                                         @Param("subQueryFlag") Long subQueryFlag,
                                         @Param("type") String types);

    List<Long> selectIds(@Param("parentNullFlag") Integer parentNullFlag,
                         @Param("subQueryFlag") Long subQueryFlag,
                         @Param("type") String type);

    int updateByPrimaryKeySelective(GoodsType record);

}