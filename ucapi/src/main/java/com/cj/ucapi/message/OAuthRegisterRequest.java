package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Create Time: 2018年03月23日</p>
 * <p>@author  </p>
 **/
@Getter
@Setter
public class OAuthRegisterRequest implements Serializable {
    @JSONField(name = "app_id")
    private String appId;
    @JSONField(name = "open_id")
    private String openId;
    @JSONField(name = "union_id")
    private String unionId;
    @JSONField(name = "nick_name")
    private String nickName;
    @JSONField(name = "properties")
    private Map<String, Object> properties = new TreeMap<>();
}
