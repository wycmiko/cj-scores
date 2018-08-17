package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventOptions implements Serializable {
    /**
     * True 表示推送生产环境，False 表示要推送开发环境；
     * 如果不指定则为推送生产环境
     * 但注意，JPush 服务端 SDK 默认设置为推送 “开发环境”。
     */
    @NotNull
    @JsonProperty("apns_production")
    private Boolean apnsProduction;
}
