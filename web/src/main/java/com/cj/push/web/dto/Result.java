package com.cj.push.web.dto;

import com.cj.push.web.consts.ResultConsts;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Create Time: 2018年03月21日</p>
 **/
@Getter
@Setter
public class Result extends TResult<Object> {
    public Result(String code, String msg) {
        super(code, msg, null);
    }


    public static Result failResult(String msg,String content) {
        Result result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, msg);
        result.setData(content);
        return result;
    }
}
