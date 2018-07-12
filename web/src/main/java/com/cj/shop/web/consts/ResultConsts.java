package com.cj.shop.web.consts;

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
    //token验证失败 用户已封禁
    public static final String TOKEN_FAILURE_MSG = "err:1101";
    public static final String USER_DONE_MSG = "err:1105";
    public static final String ERR_SERVER_DATA = "服务器繁忙 请稍后再试";
    public static final String ERR_SERVER_MSG = "服务器出错了 原因：";
    //服务器错误
    public static final String SERVER_ERROR = "err:1000";
    //参数为空
    public static final String PARAM_NULL_MSG = "err:1001";



    //upload
    public static final String UPLOAD_SUCCESS = "success";
    public static final String UPLOAD_FAILURE = "upload failure see failure-info";
    public static final String UPLOAD_TYPE_FAILURE = "上传失败！文件格式不正确（jpg/jpeg/png/gif）";
    public static final String UPLOAD_SIZE_FAILURE = "上传失败！文件过大";
    public static final String UPLOAD_JPG = "jpg";
    public static final String UPLOAD_JPEG = "jpeg";
    public static final String UPLOAD_PNG = "png";
    public static final String UPLOAD_GIF = "gif";
    public static final String UPLOAD_TXT = "txt";


    public static final String TOKEN_FAILURE = "Token不合法!";
    public static final String PWD_ERROR = "用户名或密码错误!";
    public static final String CARD_NO_FAILURE = "身份证号不合法!";
    public static final String PARAM_NULL = "必传参数不能为空";
    //player
    //必须为正数
    public static final String PARAM_MUST_POSIT_NUM = "部分参数必须为正整数";


    //weichat
    public static final String OPEN_ID="openid";
    public static final String UNION_ID="unionid";
    public static final String HEAD_IMG="headimgurl";
    public static final String SEX="sex";
    public static final String NICK_NAME="nickname";
    public static final String ACCESS_TOKEN="access_token";
    public static final String WECHAT_ERRCODE="errcode";



}
