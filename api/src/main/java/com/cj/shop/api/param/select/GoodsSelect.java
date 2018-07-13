package com.cj.shop.api.param.select;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng( )
 * @date 2018/7/3
 * @since 1.0
 */
@Setter
@Getter
public class GoodsSelect implements Serializable {
    /**
     * 一级分类ID筛选
     */
    @JsonProperty("first_type_id")
    private Integer firstTypeId;
    /**
     * 二级分类ID筛选
     */
    @JsonProperty("second_type_id")
    private Integer secondTypeId;
    /**
     * 三级分类ID筛选
     */
    @JsonProperty("third_type_id")
    private Integer thirdTypeId;
    /**
     * 是否上架筛选
     * 1=上架 0=下架
     */
    @JsonProperty("sale_flag")
    private Integer saleFlag;


    @JsonProperty("brand_id")
    private Long brandId;

    /**
     * 根据商品编号查找
     */
    @JsonProperty("goods_sn")
    private String goodsSn;
    /**
     * 商品名模糊匹配
     */
    @JsonProperty("goods_name")
    private String goodsName;
    /**
     * type=all 显示全部 包含已删除
     * type=exist 只显示未删除部分
     */
    private String type;
    /**
     * 按照某字段排序：
     * sort_type=1 按照价格升序
     * sort_type=2 按照价格降序
     * sort_type=3 按照销量升序
     * sort_type=4 按照销量降序
     * sort_type=5 按照库存量升序
     * sort_type=6 按照库存量降序
     * sort_type=7 按照浏览量升序
     * sort_type=8 按照浏览量降序
     */
    @JsonProperty("sort_type")
    private Integer sortType;
    @JsonProperty("page_num")
    private Integer pageNum;
    @JsonProperty("page_size")
    private Integer pageSize;
}
