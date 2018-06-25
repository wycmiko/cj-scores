package com.cj.shop.web.cfg.schedul;

import com.cj.shop.service.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.LongAdder;

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
     * 将每日自增长编号清0
     */
    @Scheduled(cron = "0 0/20 0/1 * * ?")
    public void settingOrderNumZero() {
        LongAdder autoincrenum = NumberUtil.AUTOINCRENUM;
        autoincrenum.sumThenReset();
        log.info("reset the order num={}", autoincrenum.longValue());
    }
}
