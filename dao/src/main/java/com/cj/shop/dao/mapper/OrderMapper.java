package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.OrderWithBLOBs;
import com.cj.shop.api.response.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderWithBLOBs record);

    OrderWithBLOBs selectByPrimaryKey(Long id);

    OrderDto selectByOrderNum(@Param("orderNum") String orderNum, @Param("uid") Long uid);

    int updateByPrimaryKeySelective(OrderWithBLOBs record);

    int updateByOrderNum(OrderWithBLOBs record);

}