package com.cj.shop.api.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/10
 * @since 1.0
 */
@Getter
@Setter
public class GoodsOrder implements Serializable {
    private String s_goods_sn;
    private Integer goods_num;
}
