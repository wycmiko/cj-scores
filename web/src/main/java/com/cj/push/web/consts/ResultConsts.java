package com.cj.push.web.consts;

/**
 * 结果信息常量类
 *
 * @author yuchuanWeng(wycmiko@foxmail.com)
 * @create 2018/3/22
 * @since 1.0
 */
public abstract class ResultConsts {


    // status code
    public static final String REQUEST_SUCCEED_STATUS = "0";
    public static final String REQUEST_FAILURE_STATUS = "1";


    // response message
    public static final String RESPONSE_SUCCEED_MSG = "success";
    public static final String RESPONSE_FAILURE_MSG = "ERROR";

    public static final String ERR_SERVER_DATA = "服务器繁忙 请稍后再试";
    public static final String ERR_SERVER_MSG = "服务器繁忙 请稍后再试!--错误信息:";
    //服务器错误
    public static final String SERVER_ERROR = "err:1000";
    //参数为空
    public static final String PARAM_NULL_MSG = "err:1001";
    //重复提交
    public static final String DUPLICATED_MOTIVE = "err:1002";
    //token验证失败
    public static final String TOKEN_FAILURE_MSG = "err:1101";
    public static final String IP_FAILURE_MSG = "err:1102";
    //用户已封禁
    public static final String USER_DONE_MSG = "err:1105";


    public static final String DUPLICATED_SUBMIT="您操作太频繁了哦";
    public static final String IP_NOT_ALLOWED="请联系管理员开通操作权限";

    public static final String PARAM_NULL = "必传参数不能为空";
    //player
    //必须为正数
    public static final String PARAM_MUST_POSIT_NUM = "部分参数必须为正整数";



}
