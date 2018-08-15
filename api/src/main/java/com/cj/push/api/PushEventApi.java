package com.cj.push.api;

import com.cj.push.api.pojo.PushEvent;

/**
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
public interface PushEventApi {

    /**
     * 插入PushEvent对象
     */
    void insert(PushEvent pushEvent);

}
