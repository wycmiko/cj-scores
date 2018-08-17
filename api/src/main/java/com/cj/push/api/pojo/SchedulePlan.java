package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/17
 * @since 1.0
 */
@Getter
@Setter
public class SchedulePlan implements Serializable {
    private String _id;

    @JsonProperty("schedule_id")
    private String scheduleId;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("schedule_time")
    private String scheduleTime;
}
