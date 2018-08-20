package com.cj.push.web.controller;

import com.alibaba.fastjson.JSON;
import com.cj.push.api.pojo.PagedList;
import com.cj.push.api.pojo.PushEvent;
import com.cj.push.api.pojo.Result;
import com.cj.push.api.pojo.SchedulePlan;
import com.cj.push.service.consts.ResultConsts;
import com.cj.push.service.impl.PushEventService;
import com.cj.push.service.util.ResultUtil;
import com.cj.push.web.validator.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yuchuanWeng
 * @date 2018/8/16
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/v1/push/event")
public class EventController {
    @Autowired
    private PushEventService pushEventService;


    /**
     * 查询推送事件列表
     * @param page_num
     * @param page_size
     * @param msg
     * @return
     */
    @GetMapping("/eventList")
    public Result getEventList(Integer page_num, Integer page_size, String msg) {
        if (CommandValidator.isEmpty(page_num, page_size)) {
            return ResultUtil.paramNullResult();
        }
        log.info("getEventList page_num={}, page_size={}, msg={}", page_num, page_size, msg);
        return ResultUtil.getResult(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG,
                pushEventService.getAllEvents(page_num, page_size, msg));
    }


    /**
     * 推送事件
     * @param pushEvent
     * @param error
     * @return
     */
    @PostMapping("/send")
    public Result sendEvent(@Valid @RequestBody PushEvent pushEvent, BindingResult error) {
        if (error.hasErrors()) {
            return ResultUtil.paramNullResult();
        }
        log.info("sendEvent body={} begin", JSON.toJSONString(pushEvent));
        Result insert = pushEventService.insert(pushEvent);
        log.info("sendEvent  end result={}", JSON.toJSONString(insert));
        return insert;
    }


    /**
     * 查询定时任务列表
     * @param msg 根据标题模糊筛选
     * @return
     */
    @GetMapping("/getScheduleList")
    public Result getScheduleList(Integer page_num, Integer page_size, String msg) {
        if (CommandValidator.isEmpty(page_num, page_size)) {
            return ResultUtil.paramNullResult();
        }
        log.info("getScheduleList  begin");
        PagedList<SchedulePlan> scheduleList = pushEventService.getScheduleList(page_num, page_size, msg);
        log.info("getScheduleList  end ");
        return ResultUtil.getResult(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG,
                scheduleList);
    }

    /**
     * 删除定时任务
     * @return
     */
    @DeleteMapping("/deleteSchedule")
    public Result deleteSchedule(String schedule_id) {
        if (CommandValidator.isEmpty(schedule_id)) {
            return ResultUtil.paramNullResult();
        }
        log.info("getScheduleList  begin");
        Result result = pushEventService.cancelSchedule(schedule_id);
        log.info("getScheduleList  end ");
        return result;
    }


}
