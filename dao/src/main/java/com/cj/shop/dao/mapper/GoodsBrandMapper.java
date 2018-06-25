package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsBrand record);

    GoodsBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsBrand record);

}