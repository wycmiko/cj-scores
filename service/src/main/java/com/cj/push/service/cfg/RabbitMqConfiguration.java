package com.cj.push.service.cfg;

import com.cj.push.api.consts.QueueEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq-事件队列
 *
 * @author yuchuanWeng
 * @date 2018/08/16
 * @since 1.0
 */
@Configuration
public class RabbitMqConfiguration {

    /**
     * 消息中心实际消费队列配置
     *
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return new Queue(QueueEnum.EVENT_QUEUE.getName());
    }




}
