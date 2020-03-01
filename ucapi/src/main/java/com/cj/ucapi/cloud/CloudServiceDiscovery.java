package com.cj.ucapi.cloud;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description: TODO
 * @date 2017/12/6
 */
public interface CloudServiceDiscovery {
    List<CloudServiceInstance> getServices(String serviceName, boolean online);

    List<CloudServiceInstance> availableServices(String serviceName, String version);
}
