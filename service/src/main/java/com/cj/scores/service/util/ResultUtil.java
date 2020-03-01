package com.cj.scores.service.util;

import com.cj.scores.api.consts.ResultConsts;
import com.cj.scores.api.pojo.Result;

/**
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

    public static Result getServerErrorResult() {
        return getResult(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.RESPONSE_FAILURE_MSG, ResultConsts.ERR_SERVER_MSG);
    }

    public static Result getDmlResult(int i) {
        if (i > 0) {
            return getResult(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, ResultConsts.RESPONSE_SUCCEED_DATA);
        }
        return getResult(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.RESPONSE_FAILURE_MSG, ResultConsts.RESPONSE_FAILURE_DATA);
    }

}
