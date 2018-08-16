package com.cj.push.api.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Create Time: 2018年03月21日</p>
 **/
@Getter
@Setter
public class Result extends TResult<Object> {
    public Result(String code, String msg, Object data) {
        super(code, msg, data);
    }


    public Result(String code, String msg) {
        super(code, msg, null);
    }
}
