package com.cj.shop.ucapi.cloud;

import com.netflix.loadbalancer.Server;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/12/7
 */
@Getter
@Setter
public class CloudServiceInstance extends Server {
    /**
     * 服务名称
     */
    private String name;

    /**
     * 节点
     */
    private String node;

    /**
     * 数据中心
     */
    private String dataCenter;

    /**
     * 版本
     */
    private String version;

    public CloudServiceInstance(String scheme, String host, int port) {
        super(scheme, host, port);
    }
}
