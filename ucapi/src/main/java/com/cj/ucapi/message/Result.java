package com.cj.ucapi.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Create Time: 2018年03月22日</p>
 * <p>@author  </p>
 **/
@Getter
@Setter
@ToString
public class Result<T> {
    private Status status;
    private String message;
    private T obj;

    public Result(Status status, String msg, T obj) {
        this.status = status;
        this.message = msg;
        this.obj = obj;
    }
}


