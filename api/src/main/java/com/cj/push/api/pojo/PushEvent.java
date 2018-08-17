package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Getter
@Setter
public class PushEvent {
    private String id;
    @NotBlank
    @JsonProperty("event_name")
    private String eventName;
    @NotBlank
    @JsonProperty("create_time")
    private String createTime;
    /**
     * 模块来源
     */
    @NotBlank
    private String source;
    /**
     * 推送目标群体
     */
    @Valid
    private Target target;
    /**
     * 数据体
     */
    @Valid
    private EventData data;

    /**
     * 1=h5 2=app 消息类型
     */
    @NotNull
    @JsonProperty("msg_type")
    private int msgType;
    /**
     * 推送时间
     */
    @NotBlank
    @JsonProperty("push_time")
    private String pushTime;

}
