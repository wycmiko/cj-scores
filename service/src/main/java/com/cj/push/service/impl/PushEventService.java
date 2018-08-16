package com.cj.push.service.impl;

import com.cj.push.api.PushEventApi;
import com.cj.push.api.pojo.PushEvent;
import com.cj.push.service.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查询全部事件
     * @return
     */
    public List<PushEvent>  getAllEvents(){
        List<PushEvent> pushEvents = ValidatorUtil.checkNotEmptyList(mongoTemplate.findAll(PushEvent.class));
        return pushEvents;
    }
}
