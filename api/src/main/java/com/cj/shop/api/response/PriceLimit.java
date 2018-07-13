package com.cj.shop.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 价格临界值实体
 * @author yuchuanWeng( )
 * @date 2018/7/2
 * @since 1.0
 */
@Data
public class PriceLimit implements Serializable {
    @JsonProperty("min_cost_price")
    private Double minCostPrice;
    @JsonProperty("max_cost_price")
    private Double maxCostPrice;
    @JsonProperty("min_sell_price")
    private Double minSellPrice;
    @JsonProperty("max_sell_price")
    private Double maxSellPrice;
}
