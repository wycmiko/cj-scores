package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuchuanWeng( )
 * @date 2018/6/25
 * @since 1.0
 */
@Getter
@Setter
public class GoodsSupplyRequest implements Serializable {
    private Long id;
    @JsonProperty("supply_name")
    private String supplyName;
    @JsonProperty("supply_tel")
    private String supplyTel;

    @JsonProperty("supply_mem")
    private String supplyMem;

    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("supply_addr")
    private String supplyAddr;
    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;

    private Map<String,Object> properties;
}
