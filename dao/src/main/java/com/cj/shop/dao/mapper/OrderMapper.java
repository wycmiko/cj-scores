package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.Order;
import com.cj.shop.api.entity.OrderWithBLOBs;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderWithBLOBs record);

    int insertSelective(OrderWithBLOBs record);

    OrderWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderWithBLOBs record);

}