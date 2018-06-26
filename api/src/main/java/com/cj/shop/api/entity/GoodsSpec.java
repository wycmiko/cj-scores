package com.cj.shop.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsSpec implements Serializable{
    private Long id;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;

}