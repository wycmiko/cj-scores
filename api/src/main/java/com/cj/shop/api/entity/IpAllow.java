package com.cj.shop.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/7/20
 * @since 1.0
 */
@Getter
@Setter
public class IpAllow implements Serializable {
    private Long id;
    private String ip;
    private Integer enabled;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
}
