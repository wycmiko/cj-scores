package com.cj.shop.api.param.select;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 库存查询参数
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/2
 * @since 1.0
 */
@Getter
@Setter
public class StockSelect implements Serializable {
    /**
     * 按照商品编号筛选
     */
    @JsonProperty("good_sn")
    private String goodSn;

    /**
     * 按照小商品编号查询
     */
    @JsonProperty("s_good_sn")
    private String sGoodSn;

    /**
     * 按照规格名模糊匹配
     */
    @JsonProperty("spec_name")
    private String specName;
    /**
     * type=all 显示全部 包含已删除
     * type=exist 只显示未删除部分
     */
    private String type;
    /**
     * 按照某字段排序：
     * sort_type=1 按照库存量升序
     * sort_type=2 按照库存量降序
     */
    @JsonProperty("sort_type")
    private Integer sortType;
    @JsonProperty("page_num")
    private Integer pageNum;
    @JsonProperty("page_size")
    private Integer pageSize;
}
