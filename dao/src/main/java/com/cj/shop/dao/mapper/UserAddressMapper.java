package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserAddress record);

    List<UserAddress> selectAll(Long uid);

    UserAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAddress record);

}