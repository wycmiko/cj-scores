package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年05月23日</p>
 * <p>@author  </p>
 **/
@Getter
@Setter
public class HandleRequest implements Serializable {
    @JSONField(name = "handle")
    private String handle;
    @JSONField(name= "body")
    private String body;
}
