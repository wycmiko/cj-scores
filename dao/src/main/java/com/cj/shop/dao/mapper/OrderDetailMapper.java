package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.OrderDetailWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderDetailWithBLOBs record);

    OrderDetailWithBLOBs selectByPrimaryKey(Long id);

    OrderDetailWithBLOBs selectByOrderDetailNum(Long id);

    int updateByPrimaryKeySelective(OrderDetailWithBLOBs record);

    int updateDetailByOrderNum(OrderDetailWithBLOBs record);

}