package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.GoodsCommentWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GoodsCommentWithBLOBs record);

    GoodsCommentWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsCommentWithBLOBs record);

}