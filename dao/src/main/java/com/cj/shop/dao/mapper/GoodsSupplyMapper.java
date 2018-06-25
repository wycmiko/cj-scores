package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsSupply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GoodsSupplyMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsSupply record);

    GoodsSupply selectByPrimaryKey(Long id);

    List<Long> selectAllSupplyIds(@Param("supplyName") String supplyName, @Param("adminFlag")Integer adminFlag);

    int updateByPrimaryKeySelective(GoodsSupply record);

}