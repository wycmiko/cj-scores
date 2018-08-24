package com.cj.ucapi.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Create Time: 2018年05月23日</p>
 * <p>@author tangxd</p>
 **/
@Setter
@Getter
public class UpdateNicknameRequest implements Serializable {
    @JSONField(name = "uid")
    private long uid;
    @JSONField(name = "nick_name")
    private String nickName;
    @JSONField(name = "properties")
    private Map<String,Object> properties = new HashMap<>();
}
