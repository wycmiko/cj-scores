package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.OrderDetailWithBLOBs;
import com.cj.shop.api.param.select.OrderSelect;
import com.cj.shop.api.response.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderDetailWithBLOBs record);

    OrderDetailWithBLOBs selectByPrimaryKey(Long id);

    OrderDetailDto selectByOrderDetailNum(@Param("orderNum") String orderNum, @Param("uid") Long uid);

    int updateByPrimaryKeySelective(OrderDetailWithBLOBs record);

    List<String> getOrderNumLists(OrderSelect select);

    int updateDetailByOrderNum(OrderDetailWithBLOBs record);

}