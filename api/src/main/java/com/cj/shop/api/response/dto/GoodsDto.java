package com.cj.shop.api.response.dto;

import com.cj.shop.api.entity.GoodsTag;
import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.crab2died.annotation.ExcelField;
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
public class GoodsDto extends PropertyEntity implements Serializable {
    @ExcelField(title = "主键ID",order = 1)
    private Long id;
    /**
     * 商品大编号
     */
    @ExcelField(title = "商品大编号", order = 2)
    @JsonProperty("goods_sn")
    private String goodsSn;
    /**
     * 商品名
     */
    @ExcelField(title = "商品名", order = 3)
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
    @ExcelField(title = "品牌名", order = 4)
    @JsonProperty("brand_name")
    private String brandName;
    /**
     * 浏览量
     */
    @ExcelField(title = "浏览量", order = 5)
    private Long pv;
    /**
     * 店铺ID以及店铺名
     */
    @JsonProperty("shop_id")
    private Long shopId;
    @ExcelField(title = "店铺名", order = 6)
    @JsonProperty("shop_name")
    private String shopName;
    /**
     * 上架时间
     */
    @ExcelField(title = "上架时间", order = 7)
    @JsonProperty("sale_time")
    private String saleTime;
    /**
     * 商品单位ID以及单位名
     */
    @JsonProperty("unit_id")
    private Long unitId;
    @ExcelField(title = "单位名", order = 8)
    @JsonProperty("unit_name")
    private String unitName;
    /**
     * 商品供应商ID以及供应商名
     */
    @JsonProperty("supply_id")
    private Long supplyId;
    @ExcelField(title = "供应商", order = 9)
    @JsonProperty("supply_name")
    private String supplyName;
    /**
     * 商品标题
     */
    @ExcelField(title = "标题", order = 10)
    @JsonProperty("goods_title")
    private String goodsTitle;
    /**
     * 商品最低最高成本价
     */
    @ExcelField(title = "规格最低成本价", order = 5)
    @JsonProperty("min_cost_price")
    private Double minCostPrice;
    @ExcelField(title = "规格最高成本价", order = 5)
    @JsonProperty("max_cost_price")
    private Double maxCostPrice;
    /**
     * 商品最低最高零售价
     */
    @ExcelField(title = "规格最低零售价", order = 6)
    @JsonProperty("min_sell_price")
    private Double minSellPrice;
    @ExcelField(title = "规格最高零售价", order = 6)
    @JsonProperty("max_sell_price")
    private Double maxSellPrice;
    /**
     * 商品总销量
     */
    @ExcelField(title = "销量", order = 7)
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
     * 商品总预警标记 1=库存充足 2=库存紧张 3=已售完
     */
    @JsonProperty("warn_stock_flag")
    private Integer warnStockFlag;
    /**
     * 1=上架 0=下架
     */
    @JsonProperty("sale_flag")
    private Integer saleFlag;
    /**
     * 状态描述
     */
    @ExcelField(title = "状态", order = 7)
    @JsonProperty("sale_flag_desc")
    private String saleFlagDesc;
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
    private Long firstTypeId;
    @ExcelField(title = "一级分类名", order = 7)
    @JsonProperty("first_type_name")
    private String firstTypeName;

    @JsonProperty("second_type_id")
    private Long secondTypeId;
    @ExcelField(title = "二级分类名", order = 7)
    @JsonProperty("second_type_name")
    private String secondTypeName;
    @JsonProperty("third_type_id")
    private Long thirdTypeId;
    @ExcelField(title = "三级分类名", order = 7)
    @JsonProperty("third_type_name")
    private String thirdTypeName;

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

    @JsonProperty("stock_list")
    private List<GoodsStockDto> stockList;

    @JsonProperty("tag_list")
    private List<GoodsTag> tagList;
}
