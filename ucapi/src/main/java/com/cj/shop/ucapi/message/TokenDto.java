package com.cj.shop.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年03月23日</p>
 * <p>@author tangxd</p>
 **/
@Getter
@Setter
public class TokenDto implements Serializable {
    private long uid;
    private String name;
    private short platform;
    private String token;
    @JSONField(name="expires_in")
    private int expiresIn;
    private long ts;
}
