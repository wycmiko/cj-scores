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
        String orderNum = String.valueOf(hashCodeV).substring(0, 5) + goodId;
        return orderNum;
    }

    /**
     * 获取小商品编号
     *
     * @return
     */
    public static String getSmallGoodsNum(String goodsSn, Long specId) {
        String smallSn = goodsSn + "-" + specId;
        return smallSn;
    }

    /**
     * 四舍五入获取总数百分比对应的整数
     * @param totalNums
     * @param ratio
     * @return
     */
    public static long getFloorNumber(int totalNums, double ratio) {
        return Math.round(totalNums*ratio);
    }


    public static void main(String[] args) {
        log.info(getOrderIdByUUId());
        log.info(getGoodsNum(12L));
        log.info(getSmallGoodsNum("9509912",1L));
    }
}
