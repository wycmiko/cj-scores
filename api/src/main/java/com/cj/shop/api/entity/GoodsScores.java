package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsScores extends PropertyEntity implements Serializable {
    private Long id;

    private Long shopId;

    private Long goodsId;

    private Double totalScores;

    private Integer scoreMem;

    private Double goodsScore;

    private Double serverScore;

    private Double expressScore;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;
}