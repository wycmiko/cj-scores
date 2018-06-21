package com.cj.shop.web.validator;


import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;

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
        Result result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.PARAM_NULL_MSG);
        result.setData(ResultConsts.PARAM_NULL);
        return result;
    }

}
