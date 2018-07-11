package com.cj.shop.api.param.select;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/11
 * @since 1.0
 */
@Getter
@Setter
public class OrderSelect implements Serializable {
    /**
     * 订单编号筛选
     */
    @JsonProperty("order_num")
    private String orderNum;

    private String token;
    private Long uid;
    /**
     * 订单状态筛选
     * 订单状态：order_status=
     * 1、待付款 = 等待买家付款
     * 2、待发货 = 等待卖家发货
     * 3、待收货 = 等待买家确认
     * 4、已完成 = 交易成功
     * 5、已关闭 = 交易关闭
     */
    private Integer status;

    @JsonProperty("page_num")
    private Integer pageNum;
    @JsonProperty("page_size")
    private Integer pageSize;
    /**
     * 支付方式筛选
     */
    @JsonProperty("pay_type")
    private Integer payType;


}
