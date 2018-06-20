package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserAddress;

public interface UserAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAddress record);

}