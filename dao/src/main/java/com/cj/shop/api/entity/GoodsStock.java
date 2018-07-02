package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class GoodsStock extends PropertyEntity implements Serializable {
    private Long id;

    private String goodsSn;

    private String sGoodsSn;

    private Long specId;

    private Integer stockNum;

    private Integer warnStockNum;

    private Boolean warnStock;

    private Boolean deleteFlag;

    private Date updateTime;

    private Date createTime;

    private Integer sortFlag;
}