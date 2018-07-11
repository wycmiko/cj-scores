package com.cj.shop.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * RabbitMQ 订单业务消息体
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/11
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMq implements Serializable {
    /**
     * type = 1  30min 未付款校验
     * type = 2  为发货后 15天 自动确认收货
     */
    private int type;
    private String orderNum;
}
