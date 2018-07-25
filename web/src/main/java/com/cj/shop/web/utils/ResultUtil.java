package com.cj.shop.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 通用结果处理类
 *
 * @author yuchuanWeng( )
 * @date 2018/5/4
 * @since 1.0
 */
@Slf4j
public class ResultUtil {
    private ResultUtil() {
    }

    /**
     * Service处理结果判定
     */
    public static Result getVaildResult(String s, Result result) {
        if (ResultMsg.HANDLER_SUCCESS.equals(s)) {
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(s);
        } else {
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.RESPONSE_FAILURE_MSG);
            result.setData(s);
        }
        return result;
    }

    public static Result getResultData(String s, Result result, String content) {
        if (ResultMsg.HANDLER_SUCCESS.equals(s)) {
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(content);
        } else {
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.RESPONSE_FAILURE_MSG);
            result.setData(content);
        }
        return result;
    }

    public static Result getVaildResultBySelf(String s, Result result, String msg, String content) {
        if (ResultMsg.HANDLER_SUCCESS.equals(s)) {
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, msg);
            result.setData(content);
        } else {
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, msg);
            result.setData(s);
        }
        return result;
    }


    public static Result getJSONObjFromVaildCode(String jsonString, Result result) {
        JSONObject jsonObject = null;
        if (!StringUtils.isEmpty(jsonString)) {
            jsonObject = JSONObject.parseObject(jsonString, JSONObject.class);
            log.info("rollback jsonString={}", jsonString);
            boolean successFlag = jsonObject != null && jsonObject.get("state") != null && Integer.parseInt(jsonObject.get("state").toString()) == 1;
            if (successFlag) {
                result.setData(jsonObject.toJSONString());
            } else {
                result.setCode(ResultConsts.REQUEST_FAILURE_STATUS);
                result.setMsg(ResultConsts.RESPONSE_FAILURE_MSG);
                result.setData(jsonObject.get("msg").toString());
            }
        }
        return result;
    }


}
