package com.cj.shop.web.consume;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Feign 调用支付服务
 * @author yuchuanWeng( )
 * @date 2018/7/12
 * @since 1.0
 */
@FeignClient(name = "match-engine")
@RequestMapping("/v1/trade")
public interface ShopMallFeign {
}
