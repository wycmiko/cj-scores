package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsBrand record);

    GoodsBrand selectByPrimaryKey(Long id);

    List<Long> selectBrandIds(@Param("brandName") String brandName, @Param("adminFlag")Integer adminFlag);

    int updateByPrimaryKeySelective(GoodsBrand record);

}