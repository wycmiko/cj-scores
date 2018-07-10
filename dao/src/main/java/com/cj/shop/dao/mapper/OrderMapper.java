package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.OrderWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderWithBLOBs record);

    int insertSelective(OrderWithBLOBs record);

    OrderWithBLOBs selectByPrimaryKey(Long id);
    OrderWithBLOBs selectByOrderNum(@Param("orderNum") String orderNum);

    int updateByPrimaryKeySelective(OrderWithBLOBs record);

    int updateByOrderNum(OrderWithBLOBs record);

}