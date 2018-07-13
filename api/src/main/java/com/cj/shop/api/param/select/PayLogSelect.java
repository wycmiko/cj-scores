package com.cj.shop.api.param.select;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付日志查询条件
 * @author yuchuanWeng( )
 * @date 2018/7/12
 * @since 1.0
 */
@Getter
@Setter
public class PayLogSelect {
    /**
     * 根据订单号筛选
     */
    @JsonProperty("order_num")
    private String orderNum;
    /**
     * 根据支付类型筛选 1=支付宝 2=微信 3=货到付款
     */
    @JsonProperty("pay_type")
    private Integer payType;
    /**
     * 根据支付状态筛选：
     * 1=支付成功 2=支付失败
     */
    private Integer status;
    @JsonProperty("page_num")
    private Integer pageNum;
    @JsonProperty("page_size")
    private Integer pageSize;
}
