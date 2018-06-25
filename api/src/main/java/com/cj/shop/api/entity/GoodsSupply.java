package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * 商品供应商实体
 */
@Getter
@Setter
public class GoodsSupply extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("supply_name")
    private String supplyName;
    @JsonProperty("supply_tel")
    private String supplyTel;

    @JsonProperty("supply_mem")
    private String supplyMem;

    @JsonProperty("supply_addr")
    private String supplyAddr;

    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
}