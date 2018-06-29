package com.cj.shop.service.util;

import com.cj.shop.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/22
 * @since 1.0
 */
@Slf4j
public class NumberUtil {

    public static final LongAdder AUTOINCRENUM = new LongAdder();
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 获取订单编号
     *
     * @return
     */
    public static String getOrderIdByUUId() {
        try {
//            lock.lock();
            int hashCodeV = UUID.randomUUID().toString().hashCode();
            if (hashCodeV < 0) {
                //避免负数
                hashCodeV = -hashCodeV;
            }
            String orderNum = DateUtils.getShortString() + String.valueOf(hashCodeV);
            AUTOINCRENUM.increment();
            String format = String.format("%09d", AUTOINCRENUM.longValue());
            return orderNum + format;
        } finally {
//            lock.unlock();
        }
    }

    /**
     * 获取商品编号
     *
     * @return
     */
    public static String getGoodsNum(Long goodId) {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            hashCodeV = -hashCodeV;
        }
        String orderNum = String.valueOf(hashCodeV).substring(0,5) + goodId;
        return orderNum;
    }


    public static void main(String[] args) {
        log.info(getOrderIdByUUId());
        log.info(getGoodsNum(12L));
    }
}
