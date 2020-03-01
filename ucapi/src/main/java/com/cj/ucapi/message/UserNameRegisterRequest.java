package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Create Time: 2018年05月23日</p>
 * <p>@author  </p>
 **/
@Getter
@Setter
public class UserNameRegisterRequest  implements Serializable {
    @JSONField(name = "user_name")
    private String userName;
    @JSONField(name = "nick_name")
    private String nickName;
    @JSONField(name = "password")
    private String password;
    @JSONField(name = "properties")
    private Map<String,Object> properties = new TreeMap<>();
}
