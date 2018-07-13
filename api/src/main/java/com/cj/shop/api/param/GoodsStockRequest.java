package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuchuanWeng( )
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
    @JsonProperty("spec_id_list")
    private List<Long> specIdList;
    @JsonProperty("stock_num")
    private Integer stockNum;
    /**
     * 预警百分比
     */
    @JsonProperty("warn_ratio")
    private Double warnRatio;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    /**
     * 销售状态 1=上架 0=下架 默认1
     */
    @JsonProperty("sale_flag")
    private Integer saleFlag;
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
