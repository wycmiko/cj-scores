package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserCart;

public interface UserCartMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserCart record);

    UserCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCart record);

}