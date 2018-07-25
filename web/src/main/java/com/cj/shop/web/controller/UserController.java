package com.cj.shop.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cj.shop.api.entity.OrderDetailWithBLOBs;
import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.param.*;
import com.cj.shop.api.param.select.OrderSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.*;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.impl.ExpressService;
import com.cj.shop.service.impl.GoodsService;
import com.cj.shop.service.impl.OrderService;
import com.cj.shop.service.impl.UserService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.consume.ShopMallFeign;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.IPAddressUtil;
import com.cj.shop.web.utils.ResultUtil;
import com.cj.shop.web.utils.UcUtil;
import com.cj.shop.web.validator.CommandValidator;
import com.cj.shop.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <title>用户服务控制层<title/>
 * 接口文档api: http://172.28.3.45:3008/project/15/interface/api  目录地址：商城前台-*
 *
 * @author yuchuanWeng
 * @date 2018/6/14
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/v1/mall/json/user")
public class UserController {
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private ShopMallFeign shopMallFeign;
    @Autowired
    private UcUtil ucUtil;

    /**
     * 查询地址详情
     *
     * @param id
     * @return
     */
    @GetMapping("/address/{id}")
    public Result getAddressDetail(@PathVariable Long id, String token) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(id, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("getAddressDetail 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            UserAddress detail = userService.getDetailById(tokenValidator.getUidByToken(token), id);
            result.setData(detail == null ? new JSONObject() : detail);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAddressDetail error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询收货地址列表
     *
     * @param token
     * @return
     */
    @GetMapping("/addressList")
    public Result addressList(String token, Integer page_num, Integer page_size) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("addressList 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(userService.getAllAddress(uid, page_num, page_size));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addressList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 添加收货地址
     *
     * @param request
     * @return
     */
    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody UserAddressRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getArea(), request.getToken(), request.getUserName(), request.getMobile(), request.getDetailAddr())
                    || !CommandValidator.isMobile(request.getMobile())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("addAddress 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            UserAddress address = new UserAddress();
            BeanUtils.copyProperties(request, address);
            address.setUid(uid);
            if (!StringUtils.isEmpty(request.getProperties())) {
                address.setProperties(JSON.toJSONString(request.getProperties()));
            } else {
                address.setProperties("{}");
            }
            String s = userService.addAddress(address);
            result = ResultUtil.getVaildResult(s, result);
            log.info("addAddress end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addAddress error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 修改收货地址(设默认)
     *
     * @param request
     * @return
     */
    @PutMapping("/updateAddr")
    public Result updateAddr(@RequestBody UserAddressRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getId(), request.getToken())
                    || !CommandValidator.isMobile(request.getMobile())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("updateAddr 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            UserAddress address = new UserAddress();
            BeanUtils.copyProperties(request, address);
            address.setUid(uid);
            String s = userService.updateAddress(address, request.getProperties());
            result = ResultUtil.getVaildResult(s, result);
            log.info("updateAddr end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateAddr error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除收货地址(设默认)
     *
     * @param token
     * @return
     */
    @DeleteMapping("/deleteAddr/{id}")
    public Result deleteAddr(@PathVariable Long id, String token) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(id, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("deleteAddr 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            String s = userService.deleteAddress(id, uid);
            result = ResultUtil.getVaildResult(s, result);
            log.info("deleteAddr end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteAddr error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }


    /**
     * 删除浏览记录
     *
     * @param token
     * @return
     */
    @DeleteMapping("/deleteVisit")
    public Result deleteVisit(String token, String type, Long visit_id) {
        //token校验
        Result result = null;
        try {
            log.info("deleteVisit begin token={},type={},visitId={}", token, type, visit_id);
            if (CommandValidator.isEmpty(type, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!"all".equals(type)) {
                if (CommandValidator.isEmpty(visit_id)) {
                    return CommandValidator.paramEmptyResult();
                }
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("deleteVisit 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            String s = userService.deleteVisit(type, uid, visit_id);
            result = ResultUtil.getVaildResult(s, result);
            log.info("deleteVisit end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteVisit error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }


    /**
     * 修改、新增 访客记录
     *
     * @param request
     * @return
     */
    @PostMapping("/updateVisit")
    public Result updateVisit(@RequestBody GoodsVisitRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getToken(), request.getGoodsId())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("updateVisit 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            request.setUid(uid);
            String s = userService.insertGoodsVisit(request);
            result = ResultUtil.getVaildResult(s, result);
            log.info("updateVisit end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateAddr error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询全部访客记录
     *
     * @param token
     * @return
     */
    @GetMapping("/visitList")
    public Result visitList(String token, Integer page_num, Integer page_size) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("visitList 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            PagedList<GoodsVisitDto> visit = userService.findAllVisit(uid, page_num, page_size);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(visit);
            log.info("visitList end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateAddr error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 加入/修改 购物车商品
     *
     * @param request
     * @return
     */
    @PostMapping("/addCart")
    public Result addCart(@RequestBody UserCartRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getToken(), request.getGoodsNum(), request.getSGoodsSn())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("addCart 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            request.setUid(uid);
            String s = userService.addCart(request);
            result = ResultUtil.getVaildResult(s, result);
            log.info("addCart end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCart error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除购物车商品
     *
     * @return
     */
    @DeleteMapping("/deleteCartItem")
    public Result deleteCartItem(@RequestBody CartDeleteRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getToken(), request.getCartList()) || request.getCartList().isEmpty()) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("deleteCartItem 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            List<Long> cartList = request.getCartList();
            String s = "";
            for (Long id : cartList) {
                s = userService.deleteFromCart(id, uid);
            }
            result = ResultUtil.getVaildResult(s, result);
            log.info("deleteCartItem end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteCartItem error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }


    /**
     * 查询全部购物车商品
     *
     * @return
     */
    @GetMapping("/getCartGoods")
    public Result getCartGoods(String token, Integer page_num, Integer page_size) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("getCartGoods 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            PagedList<UserCartDto> goodsFromCart = userService.getGoodsFromCart(uid, page_num, page_size);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(goodsFromCart);
            log.info("getCartGoods end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getCartGoods error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }


    /**
     * 用户.提交订单
     */
    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody OrderRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getToken(), request.getGoodsList(), request.getPayType(),
                    request.getAddrId()) || request.getGoodsList().isEmpty()) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("getCartGoods 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            request.setUid(uid);
            String s = orderService.insertOrder(request);
            if (s.contains("-")) {
                String orderNum = s.split("-")[1];
                OrderDetailDto dto = orderService.getOrderDetailById(orderNum, request.getUid());
                JSONObject obj = new JSONObject();
                obj.put("order_num", dto.getOrderNum());
                obj.put("order_name", dto.getOrderName());
                obj.put("order_price", dto.getOrderPrice());
                result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
                result.setData(obj);
                return result;
            }
            result = ResultUtil.getVaildResult(s, result);
            log.info("getCartGoods end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getCartGoods error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/orderDetail")
    public Result orderDetail(String token, String order_num) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(order_num, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("getCartGoods 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            OrderDetailDto detailById = orderService.getOrderDetailById(order_num, uid);
            result.setData(detailById);
            log.info("getCartGoods end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getCartGoods error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }


    /**
     * 用户取消订单 、确认收货订单
     * type =1 取消订单
     * type =2 确认收货订单
     */
    @PostMapping("/motiveOrder")
    public Result motiveOrder(@RequestBody OrderSelect orderSelect) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(orderSelect.getOrderNum(), orderSelect.getToken(), orderSelect.getType())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(orderSelect.getToken())) {
                log.info("motiveOrder 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(orderSelect.getToken());
            if (orderSelect.getType() == 1) {
                //取消订单
                log.info("uid={} cancelOrder ", uid);
                OrderDetailDto byId = orderService.getOrderDetailById(orderSelect.getOrderNum(), orderSelect.getUid());
                if (byId.getOrderStatus() == 1) {
                    OrderDetailWithBLOBs bloBs = new OrderDetailWithBLOBs();
                    bloBs.setOrderNum(orderSelect.getOrderNum());
                    bloBs.setUid(orderSelect.getUid());
                    bloBs.setOrderStatus(5);
                    String s = orderService.updateOrderStatus(bloBs);
                    result = ResultUtil.getVaildResult(s, result);
                    log.info("cancelOrder end");
                } else {
                    result = ResultUtil.getResultData(null, result, "当前状态不允许取消");
                    log.info("cancelOrder failure");
                }
            } else {
                //确认收货
                result = confirmOrder(orderSelect);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("motiveOrder error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 用户确认收货
     */
    @PostMapping("/confirmOrder")
    public Result confirmOrder(@RequestBody OrderSelect orderSelect) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(orderSelect.getOrderNum(), orderSelect.getToken())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(orderSelect.getToken())) {
                log.info("confirmOrder 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(orderSelect.getToken());
            log.info("uid={} confirmOrder ", uid);
            OrderDetailDto byId = orderService.getOrderDetailById(orderSelect.getOrderNum(), orderSelect.getUid());
            //当且仅当状态为待收货时 可以进行确认收货
            if (byId.getOrderStatus() == 3) {
                OrderDetailWithBLOBs bloBs = new OrderDetailWithBLOBs();
                bloBs.setOrderNum(orderSelect.getOrderNum());
                bloBs.setUid(orderSelect.getUid());
                bloBs.setOrderStatus(4);
                String s = orderService.updateOrderStatus(bloBs);
                result = ResultUtil.getVaildResult(s, result);
                log.info("confirmOrder end");
            } else {
                result = ResultUtil.getResultData(null, result, "当前状态不允许确认收货");
                log.info("confirmOrder failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("confirmOrder error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 用户查询订单列表
     *
     * @param select
     * @return
     */
    @PostMapping("/orderList")
    public Result orderList(@RequestBody OrderSelect select) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(select.getToken())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(select.getToken())) {
                log.info("user-orderList 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            select.setUid(tokenValidator.getUidByToken(select.getToken()));
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(orderService.getAllOrders(select));
            log.info("user-orderList end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("user-orderList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询订单物流详情
     *
     * @return
     */
    @GetMapping("/getExpressInfo")
    public Result getExpressInfo(String order_num, String token) {
        //token校验
        Result result = null;
        try {
            log.info("getExpressInfo-user begin");
            if (CommandValidator.isEmpty(order_num, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("getExpressInfo-user 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(expressService.getTraces(order_num, uid));
            log.info("getExpressInfo-user end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getExpressInfo-user error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody PayRequest request, HttpServletRequest request2) {
        //token校验
        Result result = null;
        try {
            log.info("pay begin");
            if (CommandValidator.isEmpty(request.getOutTradeNo(), request.getToken())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("pay 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            final long uid = tokenValidator.getUidByToken(request.getToken());
            OrderDetailDto orderDetailById = orderService.getOrderDetailById(request.getOutTradeNo(), uid);
            if (orderDetailById == null) {
                log.info("订单不存在");
                return ResultUtil.getResultData("error", result, ResultMsg.ORDER_NOT_EXIST);
            }
            //校验订单状态为待付款的时候才可以继续
            if (orderDetailById.getOrderStatus() != 1) {
                log.info("状态不允许");
                return ResultUtil.getResultData("error", result, "订单当前状态无法付款");
            }
            request.setAppId(ucUtil.payAppId);
            request.setIp(IPAddressUtil.getIpAddressNotInProxy(request2));
            request.setBuyerId(String.valueOf(uid));
            request.setPayChannel(String.valueOf(orderDetailById.getPayType()));
            request.setSellerId(String.valueOf(orderDetailById.getShopId()));
            request.setTotalAmount(orderDetailById.getOrderPrice());
            request.setSubject(orderDetailById.getOrderName());
            List<Long> list = new ArrayList<>();
            orderDetailById.getGoodsList().forEach((k, v) -> {
                v.stream().forEach(x -> {
                    list.add(x.getGoodsId());
                });
            });
            request.setProductId(JSON.toJSONString(list));
            request.setBody(orderDetailById.getOrderName());
            PayTradeDto pay = shopMallFeign.pay(request);
            PayTradeDtoOnly only = new PayTradeDtoOnly();
            BeanUtils.copyProperties(pay, only);
            only.setRpc_msg(pay.getMsg());
            only.setProperties(pay.getProperties());
            log.info("pay rollback={}", JSON.toJSONString(pay));
            if (ResultMsg.SUCCESS.equals(pay.getCode())) {
                result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
                result.setData(only);
            } else {
                result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.RESPONSE_FAILURE_MSG);
                result.setData(only);
            }
            log.info("pay end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("pay error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }


    @GetMapping("/queryPay")
    public Result queryPay(String app_id, String plat_trade_no, String out_trade_no, String token) {
        //token校验
        Result result = null;
        try {
            log.info("queryPay begin");
            if (CommandValidator.isEmpty(token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("queryPay 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            PayTradeDto pay = shopMallFeign.query(app_id, plat_trade_no, out_trade_no);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            PayTradeDtoOnly only = new PayTradeDtoOnly();
            BeanUtils.copyProperties(pay, only);
            result.setData(only);
            log.info("queryPay end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("queryPay error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }


}
