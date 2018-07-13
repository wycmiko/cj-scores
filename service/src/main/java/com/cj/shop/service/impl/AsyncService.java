package com.cj.shop.service.impl;

import com.cj.shop.api.param.GoodsVisitRequest;
import com.cj.shop.service.consts.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author yuchuanWeng( )
 * @date 2018/7/5
 * @since 1.0
 */
@Component
@Slf4j
public class AsyncService {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 用户访问商品详情逻辑：
     * 1、商品PV+1
     * 2、用户添加最近访问商品记录
     */
    @Async
    public Future<String> userVisit(GoodsVisitRequest request) {
        String s = userService.insertGoodsVisit(request);
        String s1 = goodsService.increPv(request.getGoodsId());
        return new AsyncResult<>(s.equals(s1) ? ResultMsg.HANDLER_SUCCESS : ResultMsg.HANDLER_FAILURE);
    }
}
