package com.cj.shop.ucapi.cloud.route;

import com.cj.shop.ucapi.cloud.CloudServiceDiscovery;
import com.cj.shop.ucapi.cloud.CloudServiceInstance;
import com.cj.shop.ucapi.cloud.ConsulCloudServiceDiscovery;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/12/8
 */
@Slf4j
public class RibbonAlgorithm implements RouterAlgorithm {

    private DynamicServerListLoadBalancer<CloudServiceInstance> loadBalancer;

    private CloudServiceDiscovery cloudServiceDiscovery;

    private String serviceName;

    public RibbonAlgorithm(ConsulCloudServiceDiscovery discovery, String serviceName) {
        this.cloudServiceDiscovery = discovery;
        this.serviceName = serviceName;
        this.init();
    }

    private void init() {
        DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues();
        config.setProperty(CommonClientConfigKey.ServerListUpdaterClassName,
                PollingServerListUpdater.class.getName());

        loadBalancer = new DynamicServerListLoadBalancer<>(config, new AvailabilityFilteringRule(),
                new DummyPing(), new CloudServerList(this.cloudServiceDiscovery, this.serviceName),
                new ZoneAffinityServerListFilter<>(),
                new PollingServerListUpdater());
    }

    @Override
    public CloudServiceInstance get() {
        Server server = null;
        try {
            server = loadBalancer.chooseServer();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        if (null == server) {
            return null;
        }
        return (CloudServiceInstance) server;
    }
}
