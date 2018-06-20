package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GoodsWithBLOBs extends PropertyEntity implements Serializable {

    private Long id;

    private Long goodsSn;

    private String goodsName;

    private Long brandId;

    private Long shopId;

    private String goodsTitle;

    private Double marketPrice;

    private Double shopPrice;

    private Integer saleCount;

    private Integer bookFlag;

    private Integer stockNum;

    private Integer warnStock;

    private Integer warnStockFlag;

    private Integer saleFlag;

    private Integer hotFlag;

    private Integer newFlag;

    private Integer recommandFlag;

    private Integer bestFlag;

    private String recomDesc;

    private Integer firstTypeId;

    private Integer secondTypeId;

    private Integer goodStatus;

    private String previewImg;

    private Integer teamBuyFlag;

    private Integer secondGoodsId;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;
    /**
     * json-商品描述
     */
    private String goodDesc;

    private String keyWords;
}