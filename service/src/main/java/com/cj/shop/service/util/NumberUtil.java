package com.cj.shop.service.util;

import com.cj.shop.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;

/**
 * 编号处理工具类
 * @author yuchuanWeng
 * @date 2018/6/22
 * @since 1.0
 */
@Slf4j
public class NumberUtil {

    public static final LongAdder ORDER_AUTOINCRENUM = new LongAdder();
    public static final LongAdder GOODS_AUTOINCRENUM = new LongAdder();
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    /**
     * 创建订单-获取订单编号
     * rule: yyyyMMddHHmmss(14bit)+防重Hash(9bit)+当日自增长订单笔数(9bit) = 32 bit
     * Demo: 20180712093009 199271600 000000001
     * @return 32位订单编号
     */
    public static String getOrderIdByUUId() {
        try {
            //can lock
            int hashCodeV = UUID.randomUUID().toString().hashCode();
            if (hashCodeV < 0) {
                //避免负数
                hashCodeV = -hashCodeV;
            }
            String s = String.valueOf(hashCodeV);
            if (s.length() >= 9) {
                s = s.substring(0,9);
            } else {
                s = String.format("%09d", hashCodeV);
            }
            String orderNum = DateUtils.getShortMonthDaySeconds() + s;
            ORDER_AUTOINCRENUM.increment();
            String format2 = String.format("%09d", ORDER_AUTOINCRENUM.longValue());
            return orderNum + format2;
        } finally {
            // can unlock
        }
    }

    /**
     * 获取商品编号 7bit
     * Rule: hash
     * Demo: 1094851
     * @return
     */
    public static String getGoodsNum() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            hashCodeV = -hashCodeV;
        }
        GOODS_AUTOINCRENUM.increment();
        String s = String.valueOf(hashCodeV);
        if (s.length() >= 6) {
            s = s.substring(0,6);
        } else {
            s = String.format("%06d", hashCodeV);
        }
        String goodsNum = s + GOODS_AUTOINCRENUM.longValue();
        return goodsNum;
    }

    /**
     * 获取小商品编号
     * Rule: 大商品编号 拼接规格ID "-"拼接
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

}
