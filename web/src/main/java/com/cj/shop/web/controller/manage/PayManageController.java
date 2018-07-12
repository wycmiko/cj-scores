package com.cj.shop.web.controller.manage;

import com.cj.shop.api.param.select.PayLogSelect;
import com.cj.shop.service.impl.PayService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/12
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/v1/mall/manage/finance")
public class PayManageController {
    @Autowired
    private PayService payService;

    /**
     * 支付日志复合查询
     * @param select
     * @return
     */
    @PostMapping("/payLogList")
    public Result orderList(@RequestBody PayLogSelect select) {
        //token校验
        Result result = null;
        try {
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(payService.payLogList(select));
            log.info("payLogList end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("payLogList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG+e.getMessage());
        }
        return result;
    }
}
