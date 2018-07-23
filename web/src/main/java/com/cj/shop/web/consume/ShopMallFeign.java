package com.cj.shop.web.consume;

import com.cj.shop.api.param.PayRequest;
import com.cj.shop.api.response.dto.PayTradeDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    /**
     * 支付
     * @param request
     * @return
     */
    @PostMapping("/v1/trade/pay")
    PayTradeDto pay(@RequestBody PayRequest request);

    /**
     * 查询详细
     * @param app_id
     * @param plat_trade_no
     * @param out_trade_no
     * @return
     */
    @GetMapping("/v1/trade/query")
    PayTradeDto query(String app_id, String plat_trade_no, String out_trade_no);
}
