package com.cj.ucapi.cloud;

import com.cj.ucapi.cfg.ConsulProperties;
import com.cj.ucapi.cfg.UcApiConfig;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/12/6
 */
@Component
@Slf4j
@EnableScheduling
public class ConsulCloudServiceDiscovery implements CloudServiceDiscovery {

    private final static String VERSION_PREFIX = "cj-version";
    private final static Map<String, Map<String, List<CloudServiceInstance>>> CLOUD_SERVICES = new TreeMap<>();

    @Autowired
    private ConsulProperties consulProperties;

    @Autowired
    private UcApiConfig ucConfig;

    private ConsulClient getClient() {
        return new ConsulClient(consulProperties.getHost(), consulProperties.getPort());
    }

    @Override
    public List<CloudServiceInstance> getServices(String serviceName, boolean online) {
        Response<List<HealthService>> response = getClient().getHealthServices(serviceName, online, QueryParams.DEFAULT);
        List<CloudServiceInstance> services = new ArrayList<>();
        for (HealthService hs : response.getValue()) {
            List<String> tags = hs.getService().getTags();
            String version = null;
            for (String tag : tags) {
                if (tag.startsWith(VERSION_PREFIX)) {
                    version = tag.substring(VERSION_PREFIX.length() + 1);
                }
            }
            if (Strings.isNullOrEmpty(version)) {
                continue;
            }

            CloudServiceInstance instance = new CloudServiceInstance("tcp", hs.getService().getAddress(), hs.getService().getPort());
            instance.setName(hs.getNode().getId());
            instance.setDataCenter(hs.getNode().getDatacenter());
            instance.setNode(hs.getNode().getNode());

            instance.setVersion(version);
            services.add(instance);
        }
        return services;
    }

    @Override
    public List<CloudServiceInstance> availableServices(String serviceName, String version) {
        synchronized (CLOUD_SERVICES) {
            if (CLOUD_SERVICES.containsKey(serviceName)) {
                if (CLOUD_SERVICES.get(serviceName).containsKey(version)) {
                    return CLOUD_SERVICES.get(serviceName).get(version);
                }
            }
        }
        return new ArrayList<>();
    }

    @Scheduled(fixedDelay = 5000L)
    public void refreshCloudService() {
        Response<Map<String, List<String>>> all = getClient().getCatalogServices(QueryParams.DEFAULT);
        synchronized (CLOUD_SERVICES) {
            Map<String, List<String>> maps = all.getValue();
            CLOUD_SERVICES.clear();
            for (Map.Entry<String, List<String>> entry : maps.entrySet()) {
                if (!entry.getKey().equals(ucConfig.getServiceName())) {
                    continue;
                }
                List<CloudServiceInstance> cloudInstances = getServices(entry.getKey(), true);
                if (cloudInstances.size() == 0) {
                    continue;
                }
                Map<String, List<CloudServiceInstance>> versionServices = new TreeMap<>();
                for (CloudServiceInstance cs : cloudInstances) {
                    if (versionServices.containsKey(cs.getVersion())) {
                        versionServices.get(cs.getVersion()).add(cs);
                    } else {
                        List<CloudServiceInstance> srvs = new ArrayList<>();
                        srvs.add(cs);
                        versionServices.put(cs.getVersion(), srvs);
                    }
                }
                CLOUD_SERVICES.put(entry.getKey(), versionServices);
            }
        }
    }
}
