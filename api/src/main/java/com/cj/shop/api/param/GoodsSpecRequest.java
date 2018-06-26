package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/26
 * @since 1.0
 */
@Getter
@Setter
public class GoodsSpecRequest extends BaseRequest {
    private Long id;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("sort_flag")
    private Integer sortFlag;
    /**
     * json- 规格
     */
    @JsonProperty("spec_properties")
    private Map<String,Object> specProperties;
    /**
     * json- 尺寸
     */
    @JsonProperty("size_properties")
    private Map<String,Object> sizeProperties;


}
