package com.cj.shop.api.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/7/23
 * @since 1.0
 */
@Getter
@Setter
public class PayTradeDtoOnly implements Serializable {
    private String trade_no;
    /**
     * 平台订单号
     */
    private String plat_trade_no;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    private String product_id;
    private String buyer_id;
    private Double total_amount;
    private String create_time;
    private String status;
    private String properties;
}
