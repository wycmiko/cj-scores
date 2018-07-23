package com.cj.shop.api.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PayMqDto implements Serializable {
    /**
     * 支付宝交易号
     */
    @JsonProperty("trade_no")
    private String tradeNo;
    /**
     * 订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    /**
     * 第三方交易号
     */
    @JsonProperty("plat_trade_no")
    private String platTradeNo;

    @JsonProperty("paid_time")
    private String paidTime;
    /**
     * "SUCCESS"
     * "FAIL"
     */
    @JsonProperty("status")
    private String status;
    /**
     * 失败的话保存失败信息
     */
    private String message;

}
