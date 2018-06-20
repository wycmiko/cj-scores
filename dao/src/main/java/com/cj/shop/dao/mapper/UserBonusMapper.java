package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserBonus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBonusMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserBonus record);

    UserBonus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBonus record);

}