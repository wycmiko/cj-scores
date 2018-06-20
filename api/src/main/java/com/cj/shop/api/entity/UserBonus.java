package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserBonus extends PropertyEntity implements Serializable {
    private Long id;

    private Long uid;

    private Long scores;

    private Long spendedScores;

    private Long lockScores;

    private Long totalScores;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;
}