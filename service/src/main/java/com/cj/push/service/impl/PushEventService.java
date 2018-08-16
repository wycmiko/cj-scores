package com.cj.push.service.impl;

import com.cj.push.api.PushEventApi;
import com.cj.push.api.pojo.PagedList;
import com.cj.push.api.pojo.PushEvent;
import com.cj.push.service.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

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
     *
     * @param msg 模糊匹配内容 匹配事件名、标题、内容
     * @return
     */
    public PagedList<PushEvent> getAllEvents(int pageNum, int pageSize, String msg) {
        //page-limit
        Query query = new Query();
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        if (!StringUtils.isEmpty(msg)) {
            //模糊搜索
            Pattern pattern = Pattern.compile("^.*" + msg + ".*$", Pattern.CASE_INSENSITIVE);
            Criteria criteria = new Criteria();
            //匹配消息标题 消息体 或者事件名
            criteria.orOperator(Criteria.where("data.msgBody.title").is(pattern),
                    Criteria.where("data.msgBody.alert").is(pattern),
                    Criteria.where("eventName").is(pattern));
            query.addCriteria(criteria);
        }
        long count = mongoTemplate.count(query, PushEvent.class);

        List<PushEvent> events = ValidatorUtil.checkNotEmptyList(mongoTemplate.find(query, PushEvent.class));
        PagedList<PushEvent> pagedList = new PagedList<>(events, count, pageNum, pageSize);
        return pagedList;
    }
}
