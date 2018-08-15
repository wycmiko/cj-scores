package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Getter
@Setter
public class PushEvent {
    private String id;
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("create_time")
    private String createTime;
    /**
     * 模块来源
     */
    private String source;
    /**
     * 推送目标群体
     */
    private Target target;
    /**
     * 数据体
     */
    private EventData data;

    /**
     * 1=h5 2=app 消息类型
     */
    @JsonProperty("msg_type")
    private int msgType;
    /**
     * 推送时间
     */
    @JsonProperty("push_time")
    private String pushTime;

}
