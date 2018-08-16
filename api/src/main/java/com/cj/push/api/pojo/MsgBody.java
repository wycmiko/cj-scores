package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MsgBody implements Serializable {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 副标题
     */
    @JsonProperty("sub_title")
    private String subTitle;
    /**
     * 消息体
     */
    private String alert;
}
