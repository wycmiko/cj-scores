package com.cj.scores.api.consts;

/**
 * @date 2018/4/23
 * @since 1.0
 */
public abstract class ResultConsts {
    // status code
    public static final String REQUEST_SUCCEED_STATUS = "0";
    public static final String REQUEST_FAILURE_STATUS = "1";


    // response message
    public static final String RESPONSE_SUCCEED_MSG = "SUCCESS";
    public static final String RESPONSE_FAILURE_MSG = "ERROR";

    public static final String RESPONSE_SUCCEED_DATA = "操作成功";
    public static final String RESPONSE_FAILURE_DATA = "操作失败";

    public static final String ERR_SERVER_MSG = "服务器繁忙, 请稍后再试!";
    //服务器错误
    public static final String SERVER_ERROR = "err:1000";
    //参数为空
    public static final String PARAM_NULL = "err:1001";
    //token验证失败
    public static final String TOKEN_FAILURE_MSG = "err:1101";
    /**
     * 用户积分不足
     */
    public static final String ERR_1106 = "err:1106";

    public static final String ERR_1107 = "err:1107";

    public static final String TOKEN_FAILURE = "Token不合法!";
    //日期格式不合法
    public static final String PARAM_MSG = "必要参数为空或不合法";
    public static final String SCORES_NOT_FULL_MSG = "剩余积分不足";
    public static final String INVALID_TIME_MSG = "日期格式不合法";
    public static final String ERR_1107_MSG = "重复操作";
}
