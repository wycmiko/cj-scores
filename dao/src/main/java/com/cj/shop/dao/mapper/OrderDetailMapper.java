package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.OrderDetail;
import com.cj.shop.api.entity.OrderDetailWithBLOBs;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderDetailWithBLOBs record);

    OrderDetailWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetailWithBLOBs record);

}