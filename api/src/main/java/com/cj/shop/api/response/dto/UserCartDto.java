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
    @JsonProperty("s_goods_sn")
    private String sGoodsSn;
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
    @JsonProperty("item_total_price")
    private Double itemTotalPrice;
    /**
     * 库存预警标记
     */
    @JsonProperty("warn_stock")
    private Integer warnStock;
    /**
     * 批次号
     */
    @JsonProperty("batch_no")
    private Integer batchNo;
    @JsonProperty("sort_flag")
    private Integer sortFlag;
    /**
     * 销售状态 1=上架 0=下架 默认1
     */
    @JsonProperty("sale_flag")
    private Integer saleFlag;

    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("update_time")
    private String updateTime;
}