package com.cj.shop.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 全局运费设置
 */
@Getter
@Setter
public class ExpressCash implements Serializable {
    private Long id;
    @JsonProperty("delivery_company")
    private String deliveryCompany;
    @JsonProperty("delivery_cash")
    private Double deliveryCash;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
}