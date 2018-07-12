package com.cj.shop.dao.mapper;

import com.cj.shop.api.param.select.PayLogSelect;
import com.cj.shop.api.response.dto.PayLogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayLogMapper {
    int insertPayLog(PayLogDto dto);
    PayLogDto getDetailLog(Long id);
    List<Long> getAllLogIds(PayLogSelect select);
}
