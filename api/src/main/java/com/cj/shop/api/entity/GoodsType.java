package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsType extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("show_flag")
    private Integer showFlag;
    @JsonProperty("type_name")
    private String typeName;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    //子菜单列表
    @JsonProperty("sub_list")
    private List<GoodsType> subList = new ArrayList<>();
}