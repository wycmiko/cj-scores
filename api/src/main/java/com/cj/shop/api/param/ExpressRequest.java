package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 物流请求参数
 * @author yuchuanWeng( )
 * @date 2018/7/12
 * @since 1.0
 */
@Getter
@Setter
public class ExpressRequest {
    private Long uid;
    private String token;
    @JsonProperty("order_num")
    private String orderNum;
    @JsonProperty("express_no")
    private String expressNo;
    @JsonProperty("express_name")
    private String expressName;

}
