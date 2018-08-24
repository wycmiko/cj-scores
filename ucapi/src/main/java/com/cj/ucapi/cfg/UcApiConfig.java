package com.cj.ucapi.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Create Time: 2018年03月21日</p>
 * <p>@author tangxd</p>
 **/
@ConfigurationProperties(prefix = "ucapi")
@Component
@Getter
@Setter
public class UcApiConfig {
    private String serviceName;
    private String appId;
    private String secret;
    private int poolMaxTotal;
    private int poolMinIdle;
    private int poolMaxIdle;
    private int poolMaxWaitMillis;
    private long poolMinEvictableIdleTimeMillis;
    private boolean poolBlockWhenExhausted;
}