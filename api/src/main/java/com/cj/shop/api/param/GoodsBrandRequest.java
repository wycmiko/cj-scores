package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng( )
 * @date 2018/6/25
 * @since 1.0
 */
@Getter
@Setter
public class GoodsBrandRequest extends BaseRequest implements Serializable {
    private Long id;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
}
