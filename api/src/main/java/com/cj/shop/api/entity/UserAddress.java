package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class UserAddress extends PropertyEntity implements Serializable {
    private Long id;

    private Long uid;
    @JsonProperty("user_name")
    private String userName;

    private String mobile;

    private String tel;

    private String province;

    private String city;

    private String area;

    @JsonProperty("post_code")
    private String postCode;

    @JsonProperty("detail_addr")
    private String detailAddr;

    @JsonProperty("default_flag")
    private Integer defaultFlag;
    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("sort_flag")
    private Integer sortFlag;
}