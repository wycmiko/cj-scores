package com.cj.shop.service.impl;

import com.cj.shop.api.interf.PayApi;
import com.cj.shop.api.param.PayLogRequest;
import com.cj.shop.api.param.select.PayLogSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.PayLogDto;
import com.cj.shop.dao.mapper.PayLogMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/12
 * @since 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PayService implements PayApi {
    @Autowired
    private JedisCache jedisCache;

    @Autowired
    private PayLogMapper payLogMapper;
    public static final String PAY_LOG_KEY="cj_shop:mall:pay:log:";
    /**
     * 添加支付日志记录
     *
     * @param request
     * @return
     */
    @Override
    public String insertPayLog(PayLogRequest request) {
        PayLogDto dto = new PayLogDto();
        BeanUtils.copyProperties(request, dto);
        dto.setProperties(PropertiesUtil.addProperties(request.getProperties()));
        int i = payLogMapper.insertPayLog(dto);
        if (i > 0) {
            jedisCache.hset(PAY_LOG_KEY, dto.getId().toString(), payLogMapper.getDetailLog(dto.getId()));
        }
        return ResultMsgUtil.dmlResult(i);
    }

    /**
     * 查询支付日志详情
     *
     * @param logId
     * @return
     */
    @Override
    public PayLogDto getPayLogDetail(Long logId) {
        PayLogDto hget = jedisCache.hget(PAY_LOG_KEY, logId.toString(), PayLogDto.class);
        if (hget == null) {
            hget = payLogMapper.getDetailLog(logId);
            if (hget != null) {
                jedisCache.hset(PAY_LOG_KEY, logId.toString(), hget);
            }
        }
        return hget;
    }

    /**
     * 查询全部支付日志payLog
     *
     * @param payLogSelect 查询条件
     * @return
     */
    @Override
    public PagedList<PayLogDto> payLogList(PayLogSelect payLogSelect) {
        Page<Object> objects = null;
        List<PayLogDto> returnList = new ArrayList<>();
        if (payLogSelect.getPageNum() != null && payLogSelect.getPageSize() != null) {
            objects = PageHelper.startPage(payLogSelect.getPageNum(), payLogSelect.getPageSize());
        } else {
            payLogSelect.setPageNum(0);
            payLogSelect.setPageSize(0);
        }
        List<Long> logIds = payLogMapper.getAllLogIds(payLogSelect);
        if (logIds != null && !logIds.isEmpty()) {
            for (Long id : logIds) {
                PayLogDto byId = getPayLogDetail(id);
                returnList.add(byId);
            }
        }
        PagedList<PayLogDto> pagedList = new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), payLogSelect.getPageNum(), payLogSelect.getPageSize());
        return pagedList;
    }
}
