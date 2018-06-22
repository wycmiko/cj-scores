package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class UserCart extends PropertyEntity implements Serializable {
    private Long id;

    private Long uid;

    private Long goodsId;

    private Integer goodsNum;

    private Integer batchNo;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;

    /**
     * 加入购物车时价格
     */
    @JsonProperty("add_price")
    private Double addPrice;

    public void setAddPrice(String name) {
        this.setProperty("add_price", name);
    }

    public Double getAddPrice() {
        return (Double) this.getProperty("add_price");
    }
}