package com.cj.ucapi.client;

import com.cj.ucapi.cfg.UcApiConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <p>Create Time: 2018年03月22日</p>
 * <p>@author  </p>
 **/
public class ClientPool {

    private static GenericObjectPool<IClient> objectPool;

    public synchronized static GenericObjectPool<IClient> getPool(UcApiConfig cfg) {

        if(null != objectPool) {
            return objectPool;
        }

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        poolConfig.setMaxTotal(cfg.getPoolMaxTotal()); // 池中的最大连接数
        poolConfig.setMinIdle(cfg.getPoolMinIdle()); // 最少的空闲连接数
        poolConfig.setMaxIdle(cfg.getPoolMaxIdle()); // 最多的空闲连接数
        poolConfig.setMaxWaitMillis(cfg.getPoolMaxWaitMillis()); // 当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常 单位:毫秒数
        poolConfig.setLifo(true); // 连接池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后
        poolConfig.setMinEvictableIdleTimeMillis(cfg.getPoolMinEvictableIdleTimeMillis()); // 连接空闲的最小时间,达到此值后空闲连接可能会被移除,默认即为30分钟
        poolConfig.setBlockWhenExhausted(cfg.isPoolBlockWhenExhausted()); // 连接耗尽时是否阻塞,默认为true

        objectPool = new GenericObjectPool<>(new ClientFactory(), poolConfig);
        return objectPool;
    }
}
