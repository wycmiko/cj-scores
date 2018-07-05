package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCartRequest extends BaseRequest implements Serializable {
    private Long id;
    private Long uid;
    private String token;
    @JsonProperty("goods_id")
    private Long goodsId;
    @JsonProperty("s_goods_num")
    private String sGoodsNum;
    @JsonProperty("goods_num")
    private Integer goodsNum;
    @JsonProperty("batch_no")
    private Integer batchNo;
    @JsonProperty("sort_flag")
    private Integer sortFlag;

}