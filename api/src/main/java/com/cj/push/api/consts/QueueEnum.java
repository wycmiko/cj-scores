package com.cj.push.api.consts;

import lombok.Getter;

/**
 * @author yuchuanWeng
 * @date 2018/8/20
 * @since 1.0
 */
@Getter
public enum QueueEnum {
    EVENT_QUEUE("cj-push");

    QueueEnum(String name) {
        this.name = name;
    }
    private String name;
}
