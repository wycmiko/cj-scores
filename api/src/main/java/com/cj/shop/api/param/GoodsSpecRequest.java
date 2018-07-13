package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yuchuanWeng( )
 * @date 2018/6/26
 * @since 1.0
 */
@Getter
@Setter
public class GoodsSpecRequest{
    private Long id;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("spec_name")
    private String specName;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
}
