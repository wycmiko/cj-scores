package com.cj.push.web;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.alibaba.fastjson.JSON;
import com.cj.push.api.pojo.*;
import com.cj.push.common.utils.DateUtils;
import com.cj.push.service.impl.PushEventService;
import com.cj.push.service.util.JiGuangPushBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Slf4j
public class WebApplicationTests {

    @Autowired
    private PushEventService service;

    @Test
    public void contextLoads() throws APIConnectionException, APIRequestException {
        PushEvent event = new PushEvent();
        event.setCreateTime(DateUtils.getCommonString());
        event.setMsgType(2);
        event.setPushTime("0");
        event.setSource("match-plat");
        EventData eventData = new EventData();
        event.setEventName("测试2");
        eventData.setMsgBody(new MsgBody("title2", "subtitle2", "alert2"));
        eventData.setOptions(new EventOptions(Boolean.FALSE));
        event.setData(eventData);
        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("2");
        list.add("1");
        event.setTarget(new Target(1, 3,list));
        service.insert(event);
    }


    @Test
    public void test() throws Exception {
        PagedList<PushEvent> allEvents = service.getAllEvents(1, 20, null);
        allEvents.getList().forEach(x ->
        {
            log.info("result={}", JSON.toJSONString(x));
        });
    }

    @Autowired
    private JiGuangPushBuilder jiGuangPushUtil;

    @Test
    public void testPush() throws APIConnectionException, APIRequestException {
        JPushClient jPushClient = JiGuangPushBuilder.getJPushClient();
        List<String> alias = new ArrayList<>();
        alias.add("891");
        PushPayload payload = JiGuangPushBuilder.buildPushIosPayload(alias, "测试API44", "大标题44", null, false,
                new HashMap<>());

        //单次定时推送
//        ScheduleResult result = jPushClient.createSingleSchedule("test_schedule2", "2018-08-17 16:25:00", payload);
//        ScheduleResult result2 = jPushClient.createSingleSchedule("test_schedule3", "2018-08-17 16:25:00", payload);
        log.info("payload={}", payload);
        PushResult result = jPushClient.sendPush(payload);
        /**
         * {"msg_id":996244042,"sendno":1754861311,"statusCode":0}
         *
         * statusCode = 0 为success
         * 否则失败为:
         *
         result.error.getCode();
         result.error.getMessage();
         */
        log.info("result={} schedule={}", result, jPushClient.getScheduleList());

    }


}
