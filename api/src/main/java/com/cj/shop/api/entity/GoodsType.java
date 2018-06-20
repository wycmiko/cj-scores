package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsType extends PropertyEntity implements Serializable {
    private Long id;

    private Long parentId;

    private Integer showFlag;

    private String typeName;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;
}