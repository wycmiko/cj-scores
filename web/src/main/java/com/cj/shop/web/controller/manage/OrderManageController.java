package com.cj.shop.web.controller.manage;

import com.cj.shop.api.param.select.OrderSelect;
import com.cj.shop.service.impl.OrderService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/11
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/v1/mall/manage/order")
public class OrderManageController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/orderList")
    public Result orderList(@RequestBody OrderSelect select) {
        //token校验
        Result result = null;
        try {
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(orderService.getAllOrders(select));
            log.info("orderList end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("orderList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

}
