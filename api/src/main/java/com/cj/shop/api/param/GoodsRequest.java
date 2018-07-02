package com.cj.shop.api.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/2
 * @since 1.0
 */
@Getter
@Setter
public class GoodsRequest implements Serializable {
    private Long id;

    private String goodsSn;

    private String goodsName;

    private Long brandId;

    private Long shopId;
    private Long unitId;
    private Long supplyId;

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
    private Integer thirdTypeId;

    private Integer goodStatus;

    private String previewImg;

    private Integer teamBuyFlag;

    private Integer secondGoodsId;

    private Integer deleteFlag;

    private String updateTime;

    private String createTime;

    private Integer sortFlag;
}
