package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String title;
    /**
     * 副标题
     */
    @NotBlank
    @JsonProperty("sub_title")
    private String subTitle;
    /**
     * 消息体
     */
    @NotBlank
    private String alert;
}
