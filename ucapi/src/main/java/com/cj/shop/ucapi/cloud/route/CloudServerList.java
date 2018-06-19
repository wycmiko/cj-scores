package com.cj.shop.ucapi.cloud.route;

import com.cj.shop.ucapi.cloud.CloudServiceDiscovery;
import com.cj.shop.ucapi.cloud.CloudServiceInstance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;

import java.util.List;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/12/8
 */
public class CloudServerList extends AbstractServerList<CloudServiceInstance> {

    public CloudServerList(CloudServiceDiscovery cloudServiceDiscovery, String serviceName) {
        this.cloudServiceDiscovery = cloudServiceDiscovery;
        this.serviceName = serviceName;
    }

    private String serviceName;
    private CloudServiceDiscovery cloudServiceDiscovery;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public List<CloudServiceInstance> getInitialListOfServers() {
        return cloudServiceDiscovery.getServices(this.serviceName, true);
    }

    @Override
    public List<CloudServiceInstance> getUpdatedListOfServers() {
        return cloudServiceDiscovery.getServices(this.serviceName, true);
    }
}
