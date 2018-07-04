package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 访客记录DAO
 */
@Getter
@Setter
public class GoodsVisitRequest extends BaseRequest implements Serializable {
    private Long id;
    private Long uid;
    private String token;
    @JsonProperty("goods_id")
    private Long goodsId;
    @JsonProperty("visit_time")
    private String visitTime;
}