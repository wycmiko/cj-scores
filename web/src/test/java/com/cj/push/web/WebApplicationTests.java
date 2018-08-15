package com.cj.push.web;

import com.cj.push.api.pojo.*;
import com.cj.push.common.utils.DateUtils;
import com.cj.push.service.impl.PushEventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
        event.setMsgType(1);
        event.setPushTime("0");
        event.setSource("match-plat");
        EventData eventData = new EventData();
        event.setEventName("测试1");
        eventData.setMsgBody(new MsgBody("title1", "body1"));
        eventData.setOptions(new EventOptions(Boolean.TRUE));
        event.setData(eventData);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        event.setTarget(new Target(1, list));
        service.insert(event);
    }


    @Test
    public void test() throws Exception {
        int i = 1;
        for (; i < 10; i++) {
        }
    }



}
