package com.cj.shop.web.validator;


import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接口参数非空校验器 （不适用于低延迟场合）
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/3/23
 * @since 1.0
 */
public class CommandValidator {
    /**
     * 判断传入的多个参数 如果有一个为null 则返回true
     * 不为null返回false
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T... t) {
        for (int i = 0; i < t.length; i++) {
            if (t[i] == null) {
                return true;
            }

        }

        return false;
    }

    public static Result paramEmptyResult() {
        return paramFailuredResult(ResultConsts.PARAM_NULL);
    }

    public static Result paramFailuredResult(String msg) {
        Result result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.PARAM_NULL_MSG);
        result.setData(msg);
        return result;
    }

    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

}
