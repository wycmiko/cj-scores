package com.cj.push.web;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.schedule.ScheduleResult;
import com.alibaba.fastjson.JSON;
import com.cj.push.api.pojo.*;
import com.cj.push.common.utils.DateUtils;
import com.cj.push.service.impl.PushEventService;
import com.cj.push.service.util.JiGuangPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebApplicationTests {

    @Autowired
    private PushEventService service;

    @Test
    public void contextLoads() {
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
        event.setTarget(new Target(1, list));
        service.insert(event);
    }


    @Test
    public void test() throws Exception {
        List<PushEvent> allEvents = service.getAllEvents();
        allEvents.forEach(x ->
        {
            log.info("result={}", JSON.toJSONString(x));
        });
    }

    @Autowired
    private JiGuangPushUtil jiGuangPushUtil;

    @Test
    public void testPush() throws APIConnectionException, APIRequestException {
        JPushClient jPushClient = JiGuangPushUtil.getJPushClient();
        List<String> alias = new ArrayList<>();
        alias.add("890");
        PushPayload payload = JiGuangPushUtil.pushIOS(alias, "测试API3", "API-Title3", "API-SubTitle3", false,
                new HashMap<>());

        //单次定时推送
        ScheduleResult result = jPushClient.createSingleSchedule("test_schedule1", "2018-08-16 16:22:00", payload);

//        PushResult result = jPushClient.sendPush();
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
