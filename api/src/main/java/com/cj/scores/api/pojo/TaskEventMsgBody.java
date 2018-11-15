package com.cj.scores.api.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * 消息队列消息体
 *
 * @author yuchuanWeng
 * @date 2018/9/17
 * @since 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskEventMsgBody implements Serializable {
    private long uid;
    private String module = "scores";
    //事件类型枚举
    private String event;
    //金币数
    private int c;
    private long ts;

    public TaskEventMsgBody(long uid, String event, long ts) {
        this.uid = uid;
        this.event = event;
        this.ts = ts;
    }
}
