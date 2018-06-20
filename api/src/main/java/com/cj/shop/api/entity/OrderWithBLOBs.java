package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderWithBLOBs extends PropertyEntity implements Serializable {
    private Long id;

    private Long shopId;

    private Long uid;

    private String orderNum;

    private String orderName;

    private String previewImg;

    private Double orderPrice;

    private Integer orderStatus;

    private Double deliveryCash;

    private Integer deliveryProtect;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;

    private String goodsList;
}