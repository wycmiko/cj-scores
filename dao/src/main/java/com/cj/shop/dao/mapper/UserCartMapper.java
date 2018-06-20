package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCartMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserCart record);

    UserCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCart record);

}