package com.cj.shop.api.param;

import com.cj.shop.api.response.GoodsOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderRequest extends BaseRequest implements Serializable {
    private Long id;
    /**
     * 收货地址ID
     */
    @JsonProperty("addr_id")
    private Long addrId;

    private String token;
    @JsonProperty("delivery_time")
    private String deliveryTime;

    private Long uid;

    @JsonProperty("order_name")
    private String orderName;

    @JsonProperty("preview_img")
    private String previewImg;
    /**
     * 总金额 = 实付款
     */
    @JsonProperty("order_price")
    private Double orderPrice;
    @JsonProperty("sort_flag")
    private Integer sortFlag;

    /**
     * 是否需要发票
     */
    @JsonProperty("need_invoice")
    private Integer needInvoice;
    /**
     * 发票内容详情
     */
    @JsonProperty("invoice_desc")
    private Map<String,Object> invoiceDesc;
    /**
     * {
     *   "goods_list":[
     *     {
     *     "cart_id":1,
     *     "s_goods_sn":"7671271-4-9",
     *     "goods_num":3}
     *     ]
     * }
     */
    @JsonProperty("goods_list")
    private List<GoodsOrder> goodsList;
}