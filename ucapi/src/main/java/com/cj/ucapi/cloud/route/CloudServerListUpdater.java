package com.cj.ucapi.cloud.route;

import com.netflix.loadbalancer.ServerListUpdater;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/12/8
 */
public class CloudServerListUpdater implements ServerListUpdater {

    @Override
    public void start(UpdateAction updateAction) {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getLastUpdate() {

        return null;
    }

    @Override
    public long getDurationSinceLastUpdateMs() {
        return 0;
    }

    @Override
    public int getNumberMissedCycles() {
        return 0;
    }

    @Override
    public int getCoreThreads() {
        return 0;
    }
}
