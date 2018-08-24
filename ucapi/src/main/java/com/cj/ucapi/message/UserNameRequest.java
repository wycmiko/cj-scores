package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Create Time: 2018年03月23日</p>
 * <p>@author tangxd</p>
 **/
@Setter
@Getter
public class UserNameRequest implements Serializable {
    @JSONField(name = "user_name")
    private String userName;
    @JSONField(name = "properties")
    private Map<String,Object> properties = new TreeMap<>();
}
