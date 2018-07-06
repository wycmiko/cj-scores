package com.cj.shop.service.util;

import com.cj.shop.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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

    public static final LongAdder ORDER_AUTOINCRENUM = new LongAdder();
    public static final LongAdder GOODS_AUTOINCRENUM = new LongAdder();
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");
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
            ORDER_AUTOINCRENUM.increment();
            String format = String.format("%09d", ORDER_AUTOINCRENUM.longValue());
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
    public static String getGoodsNum() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            hashCodeV = -hashCodeV;
        }
        GOODS_AUTOINCRENUM.increment();
        String orderNum = String.valueOf(hashCodeV).substring(0, 6) + GOODS_AUTOINCRENUM.longValue();
        return orderNum;
    }

    /**
     * 获取小商品编号
     *
     * @return
     */
    public static String getSmallGoodsNum(String goodsSn, List<Long> specId) {
        StringBuilder sb = new StringBuilder();
        if (specId != null &&
                !specId.isEmpty()) {
            sb.append(goodsSn).append("-");
            for (Long id : specId) {
                sb.append(id).append("-");
            }
            sb.deleteCharAt(sb.lastIndexOf("-"));
        }
        return sb.toString();
    }

    /**
     * 四舍五入获取总数百分比对应的整数
     *
     * @param totalNums
     * @param ratio
     * @return
     */
    public static long getFloorNumber(int totalNums, double ratio) {
        return Math.round(totalNums * ratio);
    }


    public static void main(String[] args) {
        log.info(getOrderIdByUUId());
        log.info(getGoodsNum());
        List<Long> objects = new ArrayList<>();
        objects.add(1L);
        objects.add(2L);
        objects.add(3L);
        log.info(getSmallGoodsNum("9509912", objects));
    }
}
