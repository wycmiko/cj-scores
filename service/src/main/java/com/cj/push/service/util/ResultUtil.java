package com.cj.push.service.util;

import com.cj.push.api.pojo.Result;
import com.cj.push.service.consts.ResultConsts;

/**
 * @author yuchuanWeng
 * @date 2018/4/4
 * @since 1.0
 */
public class ResultUtil {



    public static Result paramNullResult() {
        return getResult(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.PARAM_NULL, ResultConsts.PARAM_MSG);
    }

    public static Result getResult(String code, String msg, Object data) {
        return new Result(code, msg, data);
    }

}
