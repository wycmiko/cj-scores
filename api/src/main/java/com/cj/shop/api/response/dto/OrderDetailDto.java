package com.cj.shop.api.response.dto;

import com.cj.shop.api.entity.OrderGoods;
import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderDetailDto extends PropertyEntity implements Serializable {
    @JsonProperty("detail_id")
    private Long detailId;
    @JsonProperty("shop_id")
    private Long shopId;
    @JsonProperty("shop_name")
    private String shopName;
    private Long uid;
    @JsonProperty("order_num")
    private String orderNum;
    @JsonProperty("order_name")
    private String orderName;
    @JsonProperty("order_price")
    private Double orderPrice;
    @JsonProperty("order_status")
    private Integer orderStatus;
    @JsonProperty("pay_status")
    private Integer payStatus;

    @JsonProperty("pay_type")
    private Integer payType;

    @JsonProperty("delivery_cash")
    private Double deliveryCash;

    @JsonProperty("delivery_protect")
    private Integer deliveryProtect;

    @JsonProperty("receive_type")
    private Integer receiveType;
    /**
     * 物流单号
     */
    @JsonProperty("express_id")
    private String expressId;

    @JsonProperty("addr_id")
    private Long addrId;
    @JsonProperty("close_time")
    private Long closeTime;

    private UserAddress address;

    @JsonProperty("coupon_price")
    private Double couponPrice;

    @JsonProperty("real_price")
    private Double realPrice;

    @JsonProperty("need_invoice")
    private Integer needInvoice;

    @JsonProperty("order_scores")
    private Double orderScores;

    @JsonProperty("delivery_time")
    private String deliveryTime;

    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("trade_no")
    private String tradeNo;
    @JsonProperty("plat_trade_no")
    private String platTradeNo;
    /**
     * json-properties
     */
    @JsonProperty("goods_list")
    Map<Long, List<OrderGoods>> goodsList;
    @JsonProperty("goods_list_json")
    private String goodsListJson;
    /**
     * json-properties
     */
    @JsonProperty("invoice_desc")
    private String invoiceDesc;
}