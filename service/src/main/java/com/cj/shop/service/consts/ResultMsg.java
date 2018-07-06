package com.cj.shop.service.consts;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/23
 * @since 1.0
 */
public class ResultMsg {
    //参数
    public static final String PARAM_NULL = "参数不能为空";
    //操作
    public static final String HANDLER_SUCCESS = "操作成功";
    public static final String HANDLER_FAILURE = "操作失败";

    public static final String BRAND_NOT_EXISTS = "品牌不存在";
    public static final String GOOD_NOT_EXISTS = "商品不存在";

    public static final String TOO_MANY_STOCKS = "不能超过最大库存数";
    public static final String NOT_EXIST_STOCK = "没有剩余库存了哦";
    public static final String GOOD_NAME_TOO_LONG = "商品名称过长";
    public static final String ADDRESS_NOT_EXISTS = "地址不存在";
    public static final String TAG_NOT_EXISTS = "标签不存在";
    public static final String TAG_ALREADY_EXISTS = "标签名已存在";
    public static final String UNIT_NOT_EXISTS = "计量单位不存在";
    public static final String STOCK_NOT_EXISTS = "该规格的库存商品不存在";
    public static final String STOCK_ALREADY_EXISTS = "该规格的商品已存在";
    public static final String SUPPLY_NOT_EXISTS = "供应商不存在";
    public static final String SPEC_NOT_EXISTS = "规格不存在";
    public static final String DEFAULT_ADDR_ALREADY_EXIST = "默认地址已存在";

    public static final String TYPE_NOT_EXISTS = "类型不存在";
    public static final String PARTYPE_NOT_EXISTS = "父类类型不存在";
    public static final String SPEC_LIST_EMPTY = "规格列表不能为空";
}
