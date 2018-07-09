package com.cj.shop.dao.mapper;

import com.cj.shop.api.entity.ExpressCash;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpressCashMapper {
    int insertExpressCash(ExpressCash expressCash);
    ExpressCash selectByPrimaryKey(Long id);
}