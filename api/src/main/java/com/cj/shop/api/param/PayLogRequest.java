package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付流水表Dao实体Bean
 *
 * @author yuchuanWeng( )
 * @date 2018/7/12
 * @since 1.0
 */
@Getter
@Setter
public class PayLogRequest extends BaseRequest implements Serializable {
    private Long id;
    private Long uid;
    /**
     * 支付渠道id
     */
    @JsonProperty("trade_no")
    private String tradeNo;
    /**
     * 支付平台id
     */
    @JsonProperty("plat_trade_no")
    private String platTradeNo;
    /**
     * 订单编号
     */
    @JsonProperty("order_num")
    private String orderNum;
    @JsonProperty("pay_type")
    private Integer payType;
    @JsonProperty("pay_status")
    private Integer payStatus;
    @JsonProperty("total_price")
    private Double totalPrice;
    @JsonProperty("pay_time")
    private Date payTime;
}
