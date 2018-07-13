package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
/**
 * 商品分类请求参数
 *
 * @author yuchuanWeng( )
 * @date 2018/6/22
 * @since 1.0
 */
@Getter
@Setter
public class GoodsTypeRequest implements Serializable {
    private Long id;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("show_flag")
    private Integer showFlag;
    @JsonProperty("type_name")
    private String typeName;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    private Map<String,Object> properties;
}
