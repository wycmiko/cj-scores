package com.cj.push.service.impl;

import com.cj.push.api.PushEventApi;
import com.cj.push.api.pojo.PushEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Service
public class PushEventService implements PushEventApi {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入PushEvent对象
     *
     * @param pushEvent
     */
    @Override
    public void insert(PushEvent pushEvent) {
        mongoTemplate.insert(pushEvent);
    }
}
