package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/29
 * @since 1.0
 */
@Getter
@Setter
public class GoodsStock extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("goods_sn")
    private String goodsSn;
    @JsonProperty("s_goods_sn")
    private String sGoodsSn;
    @JsonProperty("spec_name")
    private String specName;
    @JsonProperty("stock_num")
    private Integer stockNum;
    @JsonProperty("warn_stock_num")
    private Integer warnStockNum;
    @JsonProperty("warn_stock")
    private Integer warnStock;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
}
