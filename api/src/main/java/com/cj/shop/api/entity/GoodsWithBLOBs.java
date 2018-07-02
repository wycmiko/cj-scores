package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商品实体Bean
 */
@Getter
@Setter
public class GoodsWithBLOBs extends PropertyEntity implements Serializable {
    private Long id;
    @JsonProperty("goods_sn")
    private String goodsSn;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("shop_id")
    private Long shopId;
    @JsonProperty("unit_id")
    private Long unitId;
    @JsonProperty("supply_id")
    private Long supplyId;

    @JsonProperty("goods_title")
    private String goodsTitle;

    @JsonProperty("min_cost_price")
    private Double minCostPrice;
    @JsonProperty("max_cost_price")
    private Double maxCostPrice;
    @JsonProperty("min_sell_price")
    private Double minSellPrice;
    @JsonProperty("max_sell_price")
    private Double maxSellPrice;

    @JsonProperty("sale_count")
    private Integer saleCount;

    @JsonProperty("book_flag")
    private Integer bookFlag;

    @JsonProperty("stock_num")
    private Integer stockNum;

    @JsonProperty("warn_stock")
    private Integer warnStock;

    @JsonProperty("warn_stock_flag")
    private Integer warnStockFlag;

    @JsonProperty("sale_flag")
    private Integer saleFlag;

    @JsonProperty("hot_flag")
    private Integer hotFlag;

    @JsonProperty("new_flag")
    private Integer newFlag;

    @JsonProperty("recommand_flag")
    private Integer recommandFlag;

    @JsonProperty("best_flag")
    private Integer bestFlag;

    @JsonProperty("recom_desc")
    private String recomDesc;

    @JsonProperty("first_type_id")
    private Integer firstTypeId;

    @JsonProperty("second_type_id")
    private Integer secondTypeId;
    @JsonProperty("third_type_id")
    private Integer thirdTypeId;

    @JsonProperty("good_status")
    private Integer goodStatus;

    @JsonProperty("preview_img")
    private String previewImg;

    @JsonProperty("team_buy_flag")
    private Integer teamBuyFlag;

    @JsonProperty("second_goods_id")
    private Integer secondGoodsId;

    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("sort_flag")
    private Integer sortFlag;
    /**
     * json-商品描述 富文本
     */
    @JsonProperty("good_desc")
    private String goodDesc;
    /**
     * 搜索关键词 可放标签
     */
    @JsonProperty("key_words")
    private String keyWords;
}