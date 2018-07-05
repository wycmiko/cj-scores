package com.cj.shop.api.response.dto;

import com.cj.shop.api.param.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCartDto extends BaseRequest implements Serializable {
    @JsonProperty("cart_id")
    private Long cartId;
    /**
     * 用户UID
     */
    private Long uid;
    @JsonProperty("goods_id")
    private Long goodsId;
    /**
     * 商品名
     */
    @JsonProperty("goods_name")
    private String goodsName;
    /**
     * 商品缩略图
     */
    @JsonProperty("goods_img")
    private String goodsImg;
    /**
     * 小商品编号
     */
    @JsonProperty("s_goods_num")
    private String sGoodsNum;
    /**
     * 品牌名
     */
    @JsonProperty("brand_name")
    private String brandName;
    /**
     * 供应商名
     */
    @JsonProperty("supply_name")
    private String supplyName;
    /**
     * 规格名
     */
    @JsonProperty("spec_name")
    private String specName;
    /**
     * 商品数量
     */
    @JsonProperty("goods_num")
    private Integer goodsNum;
    /**
     * 小规格对应单价
     */
    @JsonProperty("goods_price")
    private Double goodsPrice;
    /**
     * 批次号
     */
    @JsonProperty("batch_no")
    private Integer batchNo;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
}