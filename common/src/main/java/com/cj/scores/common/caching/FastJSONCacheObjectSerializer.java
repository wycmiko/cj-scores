package com.cj.scores.common.caching;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;


/**
 * @author
 * @Description: TODO
 * @date 2017/10/27
 */
@Component
public class FastJSONCacheObjectSerializer implements CacheObjectSerializer {

    @Override
    public String serialize(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T deserialize(String str, Class<T> valueType) {
        return JSON.parseObject(str, valueType);
    }
}
