package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class UserAddress extends PropertyEntity implements Serializable {
    private Long id;

    private Long uid;

    private String userName;

    private String mobile;

    private String tel;

    private String province;

    private String city;

    private String area;

    private String postCode;

    private String detailAddr;

    private Integer defaultFlag;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;
}