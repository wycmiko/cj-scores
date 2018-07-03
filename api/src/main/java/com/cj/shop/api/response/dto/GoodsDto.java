package com.cj.shop.api.response.dto;

import com.cj.shop.api.entity.GoodsTag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/3
 * @since 1.0
 */
@Getter
@Setter
public class GoodsDto implements Serializable {
    private Long id;
    /**
     * 商品大编号
     */
    @JsonProperty("goods_sn")
    private String goodsSn;
    /**
     * 商品名
     */
    @JsonProperty("goods_name")
    private String goodsName;
    /**
     * 品牌ID
     */
    @JsonProperty("brand_id")
    private Long brandId;
    /**
     * 品牌名
     */
    @JsonProperty("brand_name")
    private String brandName;
    /**
     * 浏览量
     */
    private Long pv;
    /**
     * 店铺ID以及店铺名
     */
    @JsonProperty("shop_id")
    private Long shopId;
    @JsonProperty("shop_name")
    private String shopName;
    /**
     * 上架时间
     */
    @JsonProperty("sale_time")
    private String saleTime;
    /**
     * 商品单位ID以及单位名
     */
    @JsonProperty("unit_id")
    private Long unitId;
    @JsonProperty("unit_name")
    private String unitName;
    /**
     * 商品供应商ID以及供应商名
     */
    @JsonProperty("supply_id")
    private Long supplyId;
    @JsonProperty("supply_name")
    private String supplyName;
    /**
     * 商品标题
     */
    @JsonProperty("goods_title")
    private String goodsTitle;
    /**
     * 商品最低最高成本价
     */
    @JsonProperty("min_cost_price")
    private Double minCostPrice;
    @JsonProperty("max_cost_price")
    private Double maxCostPrice;
    /**
     * 商品最低最高零售价
     */
    @JsonProperty("min_sell_price")
    private Double minSellPrice;
    @JsonProperty("max_sell_price")
    private Double maxSellPrice;
    /**
     * 商品总销量
     */
    @JsonProperty("sale_count")
    private Integer saleCount;
    /**
     * 商品预定标记 1=已预订 0=未预定
     */
    @JsonProperty("book_flag")
    private Integer bookFlag;
    /**
     * 商品总库存量
     */
    @JsonProperty("stock_num")
    private Integer stockNum;
    /**
     * 商品总预警库存数
     */
    @JsonProperty("warn_stock")
    private Integer warnStock;
    /**
     * 商品总预警标记
     */
    @JsonProperty("warn_stock_flag")
    private Integer warnStockFlag;
    /**
     * 1=库存充足 2=库存紧张 3=已售完
     */
    @JsonProperty("sale_flag")
    private Integer saleFlag;
    /**
     * 热销标记 1=是 0=否
     */
    @JsonProperty("hot_flag")
    private Integer hotFlag;

    @JsonProperty("new_flag")
    private Integer newFlag;

    @JsonProperty("recommand_flag")
    private Integer recommandFlag;

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

    @JsonProperty("spec_list")
    private List<GoodsStockDto> specList;

    @JsonProperty("tag_list")
    private List<GoodsTag> tagList;
}
