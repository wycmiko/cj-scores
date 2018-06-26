package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsUnit extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("unit_name")
    private String unitName;

    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("sort_flag")
    private Integer sortFlag;
}