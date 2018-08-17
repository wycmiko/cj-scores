package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 事件数据实体
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Getter
@Setter
public class EventData implements Serializable {
    @Valid
    @JsonProperty("msg_body")
    private MsgBody msgBody;
    @Valid
    private EventOptions options;

    /**
     * 业务使用的key-value
     */
    @NotNull
    private Map<String,String> values = new LinkedHashMap<>();
}
