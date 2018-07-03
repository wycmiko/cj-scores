package com.cj.shop.web.cfg.schedul;

import com.cj.shop.service.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/25
 * @since 1.0
 */
@Configuration
@EnableScheduling
@Slf4j
public class NumberScheduled {

    /**
     * 将每日订单、商品自增长编号清0
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void settingOrderNumZero() {
        long order = NumberUtil.ORDER_AUTOINCRENUM.sumThenReset();
        long goods = NumberUtil.GOODS_AUTOINCRENUM.sumThenReset();
        log.info("reset the order num={} goods num={}", order, goods);
    }
}
