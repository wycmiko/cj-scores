package com.cj.ucapi.client;

import com.cj.scores.common.spring.SpringAppContext;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * <p>Create Time: 2018年03月22日</p>
 * <p>@author tangxd</p>
 **/
public class ClientFactory extends BasePooledObjectFactory<IClient> {
    @Override
    public void destroyObject(PooledObject<IClient> p) throws Exception {
        IClient client = p.getObject();
        client.shutdown();
        super.destroyObject(p);
    }

    @Override
    public IClient create() throws Exception {
        IClient client = SpringAppContext.getBean(IClient.class);
        client.init();
        return client;
    }

    @Override
    public PooledObject<IClient> wrap(IClient client) {
        return new DefaultPooledObject<>(client);
    }
}
