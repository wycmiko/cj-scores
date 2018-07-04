package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsVisit;
import com.cj.shop.api.response.dto.GoodsVisitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsVisitMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByUid(@Param("uid") Long uid);

    int insertSelective(GoodsVisit record);


    List<Long> getVisitIds(@Param("uid") Long uid);

    GoodsVisitDto selectByPrimaryKey(Long id);

    GoodsVisit selectVisitByUidGoodsId(@Param("uid") Long uid, @Param("goodsId") Long goodsId);

    int updateByPrimaryKeySelective(GoodsVisit record);

}