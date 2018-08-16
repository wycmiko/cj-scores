package com.cj.push.service.consts;

/**
 * @author yuchuanWeng( )
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

    public static final String ERR_SERVER_MSG = "服务器繁忙, 请稍后再试!--错误信息:";
    //服务器错误
    public static final String SERVER_ERROR = "err:1000";
    //参数为空
    public static final String PARAM_NULL = "err:1001";
    public static final String PARAM_MSG = "参数不能为空";
}
