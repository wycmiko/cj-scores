package com.cj.shop.ucapi.cloud;

import java.util.List;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/12/6
 */
public interface CloudServiceDiscovery {
    List<CloudServiceInstance> getServices(String serviceName, boolean online);

    List<CloudServiceInstance> availableServices(String serviceName, String version);
}
