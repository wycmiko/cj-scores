package com.cj.shop.api.response.dto;

import com.cj.shop.api.entity.OrderGoods;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 订单Dto
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/10
 * @since 1.0
 */
@Getter
@Setter
public class OrderDto implements Serializable {
    @JsonProperty("order_id")
    private Long id;
    @JsonProperty("shop_id")
    private Long shopId;
    @JsonProperty("shop_name")
    private String shopName;
    private Long uid;
    @JsonProperty("order_num")
    private String orderNum;
    @JsonProperty("order_price")
    private Double orderPrice;
    @JsonProperty("order_status")
    private Integer orderStatus;
    @JsonProperty("delivery_cash")
    private Double deliveryCash;
    @JsonProperty("delivery_protect")
    private Integer deliveryProtect=0;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("close_time")
    private Long closeTime;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    @JsonProperty("goods_list")
    private List<OrderGoods> goodsList;
}
