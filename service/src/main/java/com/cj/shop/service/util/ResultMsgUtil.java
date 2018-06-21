package com.cj.shop.service.util;

import com.cj.shop.service.consts.ResultMsg;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/5/4
 * @since 1.0
 */
public class ResultMsgUtil {
    private ResultMsgUtil(){}

    /**
     * DML 操作语句返回特定结果
     * @param i
     * @return
     */
    public static String dmlResult(int i) {
        if (i > 0) {
            return ResultMsg.HANDLER_SUCCESS;
        }
        return ResultMsg.HANDLER_FAILURE;
    }


}
