package com.cj.shop.web.controller.manage;

import com.cj.shop.api.entity.OrderDetailWithBLOBs;
import com.cj.shop.api.param.ExpressRequest;
import com.cj.shop.api.param.select.OrderSelect;
import com.cj.shop.service.impl.ExpressService;
import com.cj.shop.service.impl.OrderService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.ResultUtil;
import com.cj.shop.web.validator.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 * 接口文档api: http://172.28.3.45:3008/project/15/interface/api  目录地址：商城后台-商品*管理
 *
 * @author yuchuanWeng
 * @date 2018/7/11
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/v1/mall/manage/order")
public class OrderManageController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExpressService expressService;


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
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    /**
     * 订单发货 or 修改物流信息
     *
     * @param request
     * @return
     */
    @PostMapping("/sendGoods")
    public Result sendGoods(@RequestBody ExpressRequest request) {
        //token校验
        Result result = null;
        try {
            log.info("sendGoods begin");
            if (CommandValidator.isEmpty(request.getExpressName(), request.getExpressNo(), request.getOrderNum())) {
                return CommandValidator.paramEmptyResult();
            }
            result = ResultUtil.getVaildResult(expressService.addExpressNum(request.getOrderNum(), request.getExpressNo(), request.getExpressName()), result);
            log.info("sendGoods end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendGoods error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    /**
     * 查询支持的物流公司
     *
     * @return
     */
    @GetMapping("/getExpress")
    public Result getExpress() {
        //token校验
        Result result = null;
        try {
            log.info("getExpress begin");
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(expressService.getExpressCompany());
            log.info("getExpress end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getExpress error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    /**
     * 查询订单物流详情
     *
     * @return
     */
    @GetMapping("/getExpressInfo")
    public Result getExpressInfo(String order_num) {
        //token校验
        Result result = null;
        try {
            log.info("getExpressInfo begin");
            if (CommandValidator.isEmpty(order_num)) {
                return CommandValidator.paramEmptyResult();
            }
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(expressService.getTraces(order_num, null));
            log.info("getExpressInfo end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getExpressInfo error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    /**
     * 运营人员取消订单
     *
     * @return
     */
    @DeleteMapping("/cancelOrder")
    public Result cancelOrder(String order_num) {
        //token校验
        Result result = null;
        try {
            log.info("cancelOrder begin");
            if (CommandValidator.isEmpty(order_num)) {
                return CommandValidator.paramEmptyResult();
            }
            OrderDetailWithBLOBs bloBs = new OrderDetailWithBLOBs();
            bloBs.setOrderNum(order_num);
            bloBs.setOrderStatus(5);
            String s = orderService.updateOrderStatus(bloBs);
            result = ResultUtil.getVaildResult(s, result);
            log.info("cancelOrder end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("cancelOrder error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

}
