package com.cj.push.web.controller;

import com.cj.push.api.pojo.Result;
import com.cj.push.service.consts.ResultConsts;
import com.cj.push.service.impl.PushEventService;
import com.cj.push.service.util.ResultUtil;
import com.cj.push.web.validator.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/eventList")
    public Result getEventList(Integer page_num, Integer page_size, String msg) {
        if (CommandValidator.isEmpty(page_num, page_size)) {
            return ResultUtil.paramNullResult();
        }

        return ResultUtil.getResult(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, pushEventService.getAllEvents(page_num, page_size, msg));
    }

}
