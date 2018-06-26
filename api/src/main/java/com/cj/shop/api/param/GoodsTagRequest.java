package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsTagRequest extends BaseRequest implements Serializable {
    private Long id;
    @JsonProperty("tag_name")
    private String tagName;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
}