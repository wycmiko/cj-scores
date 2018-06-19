package com.cj.shop.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年05月23日</p>
 * <p>@author tangxd</p>
 **/
@Setter
@Getter
public class SetPasswordRequest  implements Serializable {
    @JSONField(name = "uid")
    private long uid;
    @JSONField(name = "password")
    private String password;
    private String mobile;
}
