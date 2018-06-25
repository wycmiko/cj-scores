package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商品品牌实体
 */
@Getter
@Setter
public class GoodsBrand extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;

}