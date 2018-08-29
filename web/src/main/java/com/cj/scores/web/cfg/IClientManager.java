package com.cj.scores.web.cfg;

import com.cj.ucapi.cfg.UcApiConfig;
import com.cj.ucapi.client.ClientPool;
import com.cj.ucapi.client.IClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IClientManager {

    @Autowired
    private UcApiConfig ucApiConfig;
    private volatile GenericObjectPool<IClient> pool;

    public GenericObjectPool<IClient> getPool() {
        if (pool == null) {
            pool=ClientPool.getPool(ucApiConfig);
            return pool;
        }
        return pool;
    }


}