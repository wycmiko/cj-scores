package com.cj.shop.api.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
/**
 * @author yuchuanWeng( )
 * @date 2018/6/25
 * @since 1.0
 */
@Getter
@Setter
public class BaseRequest implements Serializable {
    public Map<String,Object> properties;
}
