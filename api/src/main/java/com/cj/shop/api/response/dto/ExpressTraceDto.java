package com.cj.shop.api.response.dto;

import com.cj.shop.api.entity.Tracess;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 物流追踪Dto
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/13
 * @since 1.0
 */
@Getter
@Setter
public class ExpressTraceDto implements Serializable {
    //快递单号
    private String LogisticCode;
    //ShipperCode  快递公司简称
    private String ShipperCode;
    private String ExpressName;
    private String OrderNum;
    //物流轨迹
    private List<Tracess> Traces;
    //State
    private String State;
    private String EBusinessID;
    private String Success;
}
