package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class Express extends PropertyEntity implements Serializable{
    private Long id;

    private Long uid;

    private String orderNum;

    private String expressName;

    private String expressNum;

    private Long addrId;

    private Integer protectFlag;

    private String expressString;

    private Integer expressStatus;

    private Double deliveryCash;

    private Integer deliveryProtect;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;

}