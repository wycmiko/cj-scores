package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderDetailWithBLOBs extends PropertyEntity implements Serializable {
    private Long id;

    private Long shopId;

    private Long uid;

    private String orderNum;

    private String orderName;

    private String expressName;

    private Double orderPrice;

    private Integer orderStatus;

    /**
     * 支付渠道id
     */
    private String tradeNo;
    /**
     * 支付平台id
     */
    private String platTradeNo;

    private Integer payStatus;

    private Integer payType;

    private Double deliveryCash;

    private Integer deliveryProtect;

    private Integer receiveType;

    private String expressId;

    private Long addrId;

    private Double couponPrice;

    private Double realPrice;

    private Integer needInvoice;

    private Double orderScores;

    private String deliveryTime;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;
    /**
     * json-properties
     */
    private String goodsList;
    /**
     * json-properties
     */
    private String invoiceDesc;
    /**
     * json-properties
     */
    private String properties;

}