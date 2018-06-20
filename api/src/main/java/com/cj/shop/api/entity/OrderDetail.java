package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class OrderDetail extends PropertyEntity implements Serializable {
    private Long id;

    private Long shopId;

    private Long uid;

    private String orderNum;

    private String orderName;

    private String orderAddress;

    private Double orderPrice;

    private Integer orderStatus;

    private Integer payStatus;

    private Integer payType;

    private Double deliveryCash;

    private Integer deliveryProtect;

    private Integer receiveType;

    private Long expressId;

    private Long addrId;

    private Double couponPrice;

    private Double realPrice;

    private Integer needInvoice;

    private Double orderScores;

    private String deliveryTime;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;
}