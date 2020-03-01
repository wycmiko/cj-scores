package com.cj.scores.service.consume;

import com.alibaba.fastjson.JSON;
import com.cj.scores.api.pojo.TaskEventMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RabbitMQ - 消息队列生产者
 *
 * @author
 * @date 2018/9/17
 * @since 1.0
 */
@Component
@Slf4j
public class TaskQueueClient {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String TASK_QUEUE_NAME = "task.event.exchange";
    private static final String ROUTER_KEY = "task.event.rk";


    public void sendEvent(TaskEventMsgBody body) {
        Objects.requireNonNull(body);
        log.info("send msg={} to task queue", body.toString());
        this.amqpTemplate.convertAndSend(TASK_QUEUE_NAME, ROUTER_KEY, JSON.toJSONString(body));
    }

}
