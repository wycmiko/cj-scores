package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsSpecWithBLOBs extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("sort_flag")
    private Integer sortFlag;
    /**
     * json- 规格
     */
    @JsonProperty("spec_properties")
    private String specProperties;
    /**
     * json- 尺寸
     */
    @JsonProperty("size_properties")
    private String sizeProperties;

}