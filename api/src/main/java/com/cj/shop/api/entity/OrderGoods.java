package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 订单商品属性实体
 * @author yuchuanWeng( )
 * @date 2018/7/10
 * @since 1.0
 */
@Data
public class OrderGoods extends PropertyEntity implements Serializable {
    @JsonProperty("goods_id")
    private Long goodsId;
    @JsonProperty("goods_sn")
    private String goodsSn;
    @JsonProperty("s_goods_sn")
    private String sGoodsSn;
    @JsonProperty("preview_img")
    private String previewImg;
    @JsonProperty("supply_id")
    private Long supplyId;
    @JsonProperty("supply_name")
    private String supplyName;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("goods_num")
    private Integer goodsNum;
    /**
     * 单价
     */
    @JsonProperty("sell_price")
    private Double goodsPrice;

    @JsonProperty("spec_list")
    private List<GoodsSpecWithBLOBs> specList;
}
