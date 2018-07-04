package com.cj.shop.api.response.dto;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 访客记录DTO
 */
@Getter
@Setter
public class GoodsVisitDto extends PropertyEntity implements Serializable {
    @JsonProperty("visit_id")
    private Long visitId;
    private Long uid;
    @JsonProperty("goods_id")
    private Long goodsId;
    @JsonProperty("goods_name")
    private String goodsName;
    @JsonProperty("goods_img")
    private String goodsImg;
    @JsonProperty("min_sell_price")
    private String minSellPrice;
    @JsonProperty("max_sell_price")
    private String maxSellPrice;
    @JsonProperty("visit_time")
    private String visitTime;
}