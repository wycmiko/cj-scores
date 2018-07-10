package com.cj.shop.api.interf;

import com.cj.shop.api.param.OrderRequest;

/**
 * 订单api
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/9
 * @since 1.0
 */
public interface OrderApi {
    /**
     * 生成订单:
     * 1、生成订单
     * 2、生成对应的订单明细
     */
    String insertOrder(OrderRequest request);

    /**
     * 查询单订单详情
     */

    /**
     * 查询订单明细详情
     */


    /**
     * 修改订单状态
     */
    String updateOrderStatus(String orderId, String status);

}
