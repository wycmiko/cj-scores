package com.cj.shop.service.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/12
 * @since 1.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "cj.mall.express")
public class ExpressConfig {
    private String userId;

    private String apiKey;
}
