package com.cj.shop.api.interf;

import com.cj.shop.api.param.PayLogRequest;
import com.cj.shop.api.param.select.PayLogSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.PayLogDto;

/**
 * 支付、财务 Api
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/12
 * @since 1.0
 */
public interface PayApi {
    /**
     * 添加支付日志记录
     * @param request
     * @return
     */
    String insertPayLog(PayLogRequest request);

    /**
     * 查询支付日志详情
     * @param logId
     * @return
     */
    PayLogDto getPayLogDetail(Long logId);

    /**
     * 查询全部支付日志payLog
     * @param payLogSelect 查询条件
     * @return
     */
    PagedList<PayLogDto> payLogList(PayLogSelect payLogSelect);
}
