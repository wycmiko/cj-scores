package com.cj.push.api;

import com.cj.push.api.pojo.PagedList;
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


    /**
     * 查询全部事件
     * @param pageNum
     * @param pageSize
     * @param msg
     * @return
     */
    PagedList<PushEvent> getAllEvents(int pageNum, int pageSize, String msg);
}
