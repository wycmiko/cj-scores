package com.cj.shop.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年05月23日</p>
 * <p>@author tangxd</p>
 **/
@Getter
@Setter
public class ExistUserResponse implements Serializable {
    @JSONField(name = "existed")
    private boolean existed;
    @JSONField(name = "had_pwd")
    private boolean hadSetPwd;
    private long uid;
}
