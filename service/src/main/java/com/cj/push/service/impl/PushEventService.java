package com.cj.push.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.schedule.ScheduleResult;
import com.cj.push.api.pojo.*;
import com.cj.push.common.utils.DateUtils;
import com.cj.push.service.consts.ResultConsts;
import com.cj.push.service.util.JiGuangPushBuilder;
import com.cj.push.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Service
@Slf4j
public class PushEventService {
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 插入PushEvent对象
     *
     * @param pushEvent
     */
    public Result insert(PushEvent pushEvent) {
        Result result = null;
        @NotBlank String pushTime = pushEvent.getPushTime();
        try {
            pushEvent.setCreateTime(DateUtils.getCommonString());
            if (!"0".equals(pushTime)) {
                DateUtils.checkTimeFormatExpired(pushTime);
            }
            @Valid Target target = pushEvent.getTarget();
            if (2 == target.getPushType()) {
                //别名推送一次最多1000个
                if (target.getArray().size() > 1000) {
                    return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ALIAS_MORE, ResultConsts.ALIAS_TOO_MORE);
                }
            } else if (3 == target.getPushType()) {
                target.setArray(new ArrayList<>());
            }
            //推送类群：
            @NotNull Integer form = target.getPlatForm();
            @Valid EventData data = pushEvent.getData();
            @Valid MsgBody msgBody = data.getMsgBody();
            final PushPayload pushPayload;
            switch (form) {
                //android
                case 1:
                    pushPayload = JiGuangPushBuilder.buildPushAndroidPayload(target.getArray(), msgBody.getAlert(), msgBody.getTitle(),
                            data.getOptions().getApnsProduction(), data.getValues());
                    break;
                //ios
                case 2:
                    pushPayload = JiGuangPushBuilder.buildPushIosPayload(target.getArray(),
                            msgBody.getAlert(), msgBody.getTitle(), msgBody.getSubTitle(),
                            data.getOptions().getApnsProduction(), data.getValues());
                    break;
                //all
                case 3:
                    pushPayload = JiGuangPushBuilder.buildPushAllByAliasPayload(target.getArray(),
                            msgBody.getAlert(), msgBody.getTitle(), msgBody.getSubTitle(),
                            data.getOptions().getApnsProduction(), data.getValues());
                    break;
                default:
                    return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.PLATFORM_ERR, ResultConsts.PLATFORM_ERR_MSG);
            }

            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, ResultConsts.RESPONSE_SUCCEED_DATA);
            log.info("push pay load={}", pushPayload);
            JPushClient jPushClient = JiGuangPushBuilder.getJPushClient();
            if (!"0".equals(pushTime)) {
                //定时推送
                ScheduleResult schedule = jPushClient.createSingleSchedule(pushEvent.getEventName(), pushTime, pushPayload);
                SchedulePlan schedulePlan = new SchedulePlan();
                schedulePlan.setEventName(pushEvent.getEventName());
                schedulePlan.setScheduleId(schedule.getSchedule_id());
                schedulePlan.setType(form);
                schedulePlan.setAlert(msgBody.getAlert());
                schedulePlan.setCreateTime(DateUtils.getCommonString());
                schedulePlan.setEnabled(1);
                schedulePlan.setScheduleTime(pushTime);
                log.info("result={} insert schedule", jPushClient.getScheduleList());
                mongoTemplate.insert(schedulePlan, "SchedulePlan");
            } else {
                //实时推送
                PushResult result1 = jPushClient.sendPush(pushPayload);
                log.info("push send ios result={}", result1);
            }
            mongoTemplate.insert(pushEvent, "PushEvent");
        } catch (ParseException e) {
            //日期格式不合法
            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.INVALID_TIME, ResultConsts.INVALID_TIME_MSG);
        } catch (IllegalArgumentException e2) {
            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.INVALID_TIME_MILL, ResultConsts.INVALID_TIME_MILL_MSG);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.JIGUANG_API_CONNECT_ERR, e.getDoneRetriedTimes());
        } catch (APIRequestException e) {
            if (1011 == e.getErrorCode()) {
                return new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, ResultConsts.ALIAS_NOT_FOUND_MSG);
            }
            e.printStackTrace();
            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.JIGUANG_API_REQUEST_ERR, e.getErrorMessage());
        }
        return result;
    }

    /**
     * 分页查询全部事件
     *
     * @param msg 模糊匹配内容 匹配事件名、标题、内容
     * @return
     */
    public PagedList<PushEvent> getAllEvents(int pageNum, int pageSize, String msg) {
        //page-limit
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        //模糊搜索 事件名、标题、以及内容
        if (!StringUtils.isEmpty(msg)) {
            Pattern pattern = Pattern.compile("^.*" + msg + ".*$", Pattern.CASE_INSENSITIVE);
            Criteria criteria = new Criteria();
            //匹配消息标题 消息体 或者事件名
            criteria.orOperator(Criteria.where("data.msgBody.title").is(pattern),
                    Criteria.where("data.msgBody.alert").is(pattern),
                    Criteria.where("eventName").is(pattern));
            query.addCriteria(criteria);
        }
        long count = mongoTemplate.count(query, PushEvent.class, "PushEvent");
        List<PushEvent> events = ValidatorUtil.checkNotEmptyList(mongoTemplate.find(query, PushEvent.class, "PushEvent"));
        return new PagedList<>(events, count, pageNum, pageSize);
    }

    /**
     * 分页查询定时任务列表
     * @param pageNum
     * @param pageSize
     * @param msg
     * @return
     */
    public PagedList<SchedulePlan> getScheduleList(int pageNum, int pageSize, String msg) {
        //page-limit
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        //模糊搜索 事件名、标题、以及内容
        if (!StringUtils.isEmpty(msg)) {
            Pattern pattern = Pattern.compile("^.*" + msg + ".*$", Pattern.CASE_INSENSITIVE);
            Criteria criteria = new Criteria();
            //匹配消息标题 消息体 或者事件名
            criteria.orOperator(Criteria.where("eventName").is(pattern),
                    Criteria.where("alert").is(pattern));
            query.addCriteria(criteria);
        }
        long count = mongoTemplate.count(query, SchedulePlan.class, "SchedulePlan");

        List<SchedulePlan> events = ValidatorUtil.checkNotEmptyList(mongoTemplate.find(query, SchedulePlan.class, "SchedulePlan"));
        PagedList<SchedulePlan> pagedList = new PagedList<>(events, count, pageNum, pageSize);
        return pagedList;
    }

    public Result cancelSchedule(String scheduleId) {
        Result result = null;
        JPushClient jPushClient = JiGuangPushBuilder.getJPushClient();
        try {
            scheduleId = URLDecoder.decode(scheduleId, "UTF-8");

            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            jPushClient.deleteSchedule(scheduleId);
            //变更状态
            Query query = new Query();
            query.addCriteria(Criteria.where("scheduleId").is(scheduleId));
            mongoTemplate.updateFirst(query,
                    Update.update("enabled", 0), "SchedulePlan");
            result.setData(ResultConsts.RESPONSE_SUCCEED_DATA);
        } catch (APIConnectionException e) {
            result.setCode(ResultConsts.REQUEST_FAILURE_STATUS);
            result.setMsg(ResultConsts.JIGUANG_API_CONNECT_ERR);
            result.setData(e.getMessage());
            e.printStackTrace();
        } catch (APIRequestException e) {
            result.setCode(ResultConsts.REQUEST_FAILURE_STATUS);
            result.setMsg(ResultConsts.JIGUANG_API_REQUEST_ERR);
            result.setData("通知不存在或者已失效");
        } catch (UnsupportedEncodingException e) {
            result.setCode(ResultConsts.REQUEST_FAILURE_STATUS);
            result.setMsg(ResultConsts.SERVER_ERROR);
            result.setData(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
