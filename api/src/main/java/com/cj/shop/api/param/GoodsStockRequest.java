package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/29
 * @since 1.0
 */
@Getter
@Setter
public class GoodsStockRequest extends BaseRequest implements Serializable {
    private Long id;
    @JsonProperty("goods_sn")
    private String goodsSn;
    @JsonProperty("s_goods_sn")
    private String sGoodsSn;
    @JsonProperty("spec_id")
    private Long specId;
    @JsonProperty("stock_num")
    private Integer stockNum;
    @JsonProperty("warn_stock_num")
    private Integer warnStockNum;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    /**
     * 成本价
     */
    @JsonProperty("cost_price")
    private Double costPrice;
    /**
     * 零售价
     */
    @JsonProperty("sell_price")
    private Double sellPrice;
    /**
     * 重量
     */
    private String weight;
}
