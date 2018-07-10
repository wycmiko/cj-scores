package com.cj.shop.common.consts;

import lombok.Getter;

/**
 * 消息队列枚举
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    MESSAGE_QUEUE("message.center.direct", "order-status", "order-status"),
    /**
     * 消息通知ttl队列
     */
    MESSAGE_TTL_QUEUE("message.center.topic.ttl", "order-status-ttl", "order-status-ttl");
    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

}
