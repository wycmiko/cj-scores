package com.cj.scores.service.consts;

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

    public static final String RESPONSE_SUCCEED_DATA = "操作成功";
    public static final String RESPONSE_FAILURE_DATA = "操作失败";

    public static final String ERR_SERVER_MSG = "服务器繁忙, 请稍后再试!--错误信息:";
    //服务器错误
    public static final String SERVER_ERROR = "err:1000";
    //参数为空
    public static final String PARAM_NULL = "err:1001";


    //日期格式不合法
    public static final String INVALID_TIME = "err:1200";
    //定时任务日期小于当前日期
    public static final String INVALID_TIME_MILL = "err:1201";
    //别名数量过多
    public static final String ALIAS_MORE = "err:1202";
    //平台参数不正确
    public static final String PLATFORM_ERR = "err:1203";

    //极光推送api异常
    public static final String JIGUANG_API_ERR = "err:1301";
    public static final String JIGUANG_API_CONNECT_ERR = "err:1302";
    public static final String JIGUANG_API_REQUEST_ERR = "err:1303";
    public static final String JIGUANG_ALIAS_NOT_FOUND = "err:1304";

    public static final String PARAM_MSG = "参数不能为空";
    public static final String INVALID_TIME_MSG = "日期格式不合法";
    public static final String INVALID_TIME_MILL_MSG = "日期不能小于当前时间";
    public static final String PLATFORM_ERR_MSG = "平台参数不正确";

    public static final String ALIAS_TOO_MORE = "别名推送一次最多1000个";
    public static final String ALIAS_NOT_FOUND_MSG = "部分别名未找到，已自动滤去";
}
