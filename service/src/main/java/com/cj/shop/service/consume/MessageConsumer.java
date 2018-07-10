package com.cj.shop.service.consume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/10
 * @since 1.0
 */
@Component
@Slf4j
public class MessageConsumer {


    /**
     * 此处用于监听订单的状态：
     * 1、订单未付款30分钟自动关闭
     * 2、订单未签收15天后自动签收
     *
     * @param message
     */
    @RabbitListener(queues = "order-status")
    @RabbitHandler
    public void process(Message message) {
        try {
            final String mess = new String(message.getBody());
            log.info("订单定时回溯 判断状态 消息接受的内容:{}", mess);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
