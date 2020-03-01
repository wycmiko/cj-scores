package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Create Time: 2018年03月22日</p>
 * <p>@author  </p>
 **/
@Setter
@Getter
public class UserDto implements Serializable {
    private long uid;
    @JSONField(name = "reg_ts")
    private long regTs;
    private String name;
    private boolean banned;
    @JSONField(name = "nick_name")
    private String nickName;
    private String email;
    private Map<String,Object> properties;
}
