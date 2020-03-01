package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年05月23日</p>
 * <p>@author  </p>
 **/
@Setter
@Getter
public class SetPasswordRequest  implements Serializable {
    @JSONField(name = "uid")
    private long uid;
    @JSONField(name = "password")
    private String password;
    private String mobile;

    /**
     * 1=pc,2=app,3=other
     */
    private short platform;
}
