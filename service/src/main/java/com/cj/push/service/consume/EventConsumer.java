package com.cj.push.service.consume;

import com.alibaba.fastjson.JSON;
import com.cj.push.api.pojo.PushEvent;
import com.cj.push.api.pojo.Result;
import com.cj.push.service.impl.PushEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 事件消费者
 *
 * @author yuchuanWeng
 * @date 2018/8/16
 * @since 1.0
 */
@Component
@Slf4j
public class EventConsumer {

    @Autowired
    private PushEventService pushEventService;

    /**
     * 此处用于监听订单的状态：
     * 1、订单未付款30分钟自动关闭
     * 2、订单未签收15天后自动签收
     *
     * @param pushEvent
     */
    @RabbitListener(queues = "cj-push")
    @RabbitHandler
    public void process(String pushEvent) {
        try {
            log.info("消息队列接受消息{}", pushEvent);
            Result result = pushEventService.insert(JSON.parseObject(pushEvent, PushEvent.class));
            log.info("event queue handle result={}", JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
