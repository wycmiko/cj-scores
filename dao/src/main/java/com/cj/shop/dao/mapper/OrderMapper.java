package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.OrderWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderWithBLOBs record);

    int insertSelective(OrderWithBLOBs record);

    OrderWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderWithBLOBs record);

}