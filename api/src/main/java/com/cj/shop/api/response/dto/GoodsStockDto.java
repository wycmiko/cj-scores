package com.cj.shop.api.response.dto;

import com.cj.shop.api.entity.GoodsSpecWithBLOBs;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

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
    @JsonProperty("spec_id_list")
    private String specIdList;
    @JsonProperty("spec_list")
    private List<GoodsSpecWithBLOBs> specList;
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
    /**
     * 销售状态 1=上架 0=下架 默认1
     */
    @JsonProperty("sale_flag")
    private Integer saleFlag;
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
