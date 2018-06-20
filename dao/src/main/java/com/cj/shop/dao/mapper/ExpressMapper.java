package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.ExpressWithBLOBs;

public interface ExpressMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ExpressWithBLOBs record);

    ExpressWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressWithBLOBs record);

}