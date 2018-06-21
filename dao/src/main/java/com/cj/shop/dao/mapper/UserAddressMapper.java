package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    int deleteByPrimaryKey(@Param("uid") Long uid,@Param("id") Long id);

    int insertSelective(UserAddress record);

    List<UserAddress> selectAll(Long uid);

    List<Long> selectAllIds(Long uid);

    UserAddress selectByPrimaryKey(@Param("uid")Long uid, @Param("id") Long id);

    int updateByPrimaryKeySelective(UserAddress record);

}