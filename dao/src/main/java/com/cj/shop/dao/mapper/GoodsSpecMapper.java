package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsSpecWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsSpecMapper {
    int deleteByParentId(@Param("parentId") Long parentId);

    int insertSelective(GoodsSpecWithBLOBs record);

    GoodsSpecWithBLOBs selectByPrimaryKey(@Param("id") Long id, @Param("type") String type);

    List<Long> selectAllSpecIds(@Param("type") String type, @Param("parentId") Long parentId,
                                @Param("parentNullFlag") Integer parentNullFlag);

    int updateByPrimaryKeySelective(GoodsSpecWithBLOBs record);
}