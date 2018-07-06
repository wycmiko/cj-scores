package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.UserCart;
import com.cj.shop.api.response.dto.UserCartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserCartMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("uid") Long uid);

    int insertSelective(UserCart record);

    UserCartDto selectByPrimaryKey(@Param("id") Long id, @Param("uid") Long uid);

    UserCart selectByUidGoodsId(@Param("uid") Long uid, @Param("sGoodsId") String sGoodsId);

    List<Long> selectUserCartThings(@Param("uid") Long uid);

    int increGoodsNum(@Param("uid") Long uid, @Param("sGoodsId") String sGoodsId, @Param("num") Integer num);

    int updateByPrimaryKeySelective(UserCart record);

}