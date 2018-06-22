package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Long id);

    List<GoodsType> selectAllTopsByParam(@Param("parentNullFlag") Integer parentNullFlag,
                                         @Param("subQueryFlag")Long subQueryFlag,
                                         @Param("adminFlag")Integer adminFlag);

    List<Long> selectIds(@Param("parentNullFlag") Integer parentNullFlag,
                                         @Param("subQueryFlag")Long subQueryFlag,
                                         @Param("adminFlag")Integer adminFlag);

    int updateByPrimaryKeySelective(GoodsType record);

}