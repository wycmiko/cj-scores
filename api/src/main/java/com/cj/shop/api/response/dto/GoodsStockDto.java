package com.cj.shop.api.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/2
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class GoodsStockDto implements Serializable {
    @JsonProperty("stock_id")
    private Long stockId;
    @JsonProperty("goods_sn")
    private String goodsSn;
    @JsonProperty("s_goods_sn")
    private String sGoodsSn;
    @JsonProperty("goods_name")
    private String goodsName;
    @JsonProperty("spec_id")
    private Long specId;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("parent_name")
    private String parentName;
    @JsonProperty("spec_name")
    private String specName;
    @JsonProperty("stock_num")
    private Integer stockNum;
    @JsonProperty("warn_stock_num")
    private Integer warnStockNum;
    @JsonProperty("warn_stock")
    private Integer warnStock;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    private String properties;
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
    @JsonProperty("warn_ratio")
    private Double warnRatio;
}
