package com.cj.shop.web.cfg;

import com.cj.shop.ucapi.cfg.UcApiConfig;
import com.cj.shop.ucapi.client.ClientPool;
import com.cj.shop.ucapi.client.IClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IClientManager {

    @Autowired
    private UcApiConfig ucApiConfig;
    //private volatile GenericObjectPool<IClient> pool;

    public GenericObjectPool<IClient> getPool() {
//        if (pool == null) {
//            pool=ClientPool.getPool(ucApiConfig);
//            return pool;
//        }
//        return pool;
        return ClientPool.getPool(ucApiConfig);
    }


}