package com.cj.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cj.shop.api.entity.*;
import com.cj.shop.api.interf.OrderApi;
import com.cj.shop.api.param.OrderRequest;
import com.cj.shop.api.param.select.OrderSelect;
import com.cj.shop.api.response.GoodsOrder;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.GoodsStockDto;
import com.cj.shop.api.response.dto.OrderDetailDto;
import com.cj.shop.api.response.dto.OrderDto;
import com.cj.shop.common.consts.QueueEnum;
import com.cj.shop.dao.mapper.GoodsStockMapper;
import com.cj.shop.dao.mapper.OrderDetailMapper;
import com.cj.shop.dao.mapper.OrderMapper;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.provider.MessageProvider;
import com.cj.shop.service.util.NumberUtil;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 订单服务层
 *
 * @author yuchuanWeng()
 * @date 2018/7/10
 * @since 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class OrderService implements OrderApi {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsExtensionService goodsExtensionService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private GoodsStockMapper goodsStockMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private JedisCache jedisCache;

    public static final String ORDER_KEY = "cj_shop:mall:order:";
    public static final String ORDER_DETAIL_KEY = "cj_shop:mall:order:detail";
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 生成订单:
     * 1、生成订单
     * 2、生成对应的订单明细
     *
     * @param request
     */
    @Override
    public String insertOrder(OrderRequest request) {
        try {
            reentrantLock.lock();
            List<OrderGoods> list = new ArrayList<>();
            double totalPrice = 0.0;
            StringBuilder sb = new StringBuilder();
            for (GoodsOrder order : request.getGoodsList()) {
                OrderGoods orderGoods = new OrderGoods();
                GoodsStockDto stockDto = goodsStockMapper.selectBySgoodId(order.getS_goods_sn());
                if (stockDto == null) {
                    return ResultMsg.STOCK_NOT_EXISTS;
                }
                //1、校验库存
                if (order.getGoods_num() > stockDto.getStockNum()) {
                    return ResultMsg.NOT_ENOUGH;
                }
                if (sb.length() <= 10) {
                    sb.append(stockDto.getGoodsName());
                } else {
                    sb.append(".");
                }
                GoodsStockDto stockDto2 = goodsExtensionService.getStockById(stockDto.getStockId());
                orderGoods.setSupplyId(stockDto.getSupplyId());
                orderGoods.setGoodsId(stockDto.getGoodsId());
                orderGoods.setGoodsName(stockDto.getGoodsName());
                orderGoods.setGoodsNum(order.getGoods_num());
                orderGoods.setGoodsPrice(stockDto.getSellPrice());
                orderGoods.setPreviewImg(stockDto.getPreviewImg());
                orderGoods.setSGoodsSn(stockDto.getSGoodsSn());
                orderGoods.setGoodsSn(stockDto.getGoodsSn());
                orderGoods.setSpecList(stockDto2.getSpecList());
                orderGoods.setSupplyName(stockDto2.getSupplyName());
                list.add(orderGoods);
                totalPrice += stockDto.getSellPrice() * order.getGoods_num();
            }
            OrderWithBLOBs bloBs = new OrderWithBLOBs();
            BeanUtils.copyProperties(request, bloBs);
            //订单编号
            String orderNum = NumberUtil.getOrderIdByUUId();
            //全局运费
            ExpressCash cash = goodsExtensionService.getExpressCash();
            bloBs.setOrderName(sb.toString());
            bloBs.setOrderNum(orderNum);
            bloBs.setDeliveryCash(cash.getDeliveryCash());
            bloBs.setDeliveryProtect(0);
            log.info("uid={}, submit order price={}", request.getUid(), totalPrice);
            String goodsList = JSON.toJSONString(list);
            bloBs.setGoodsList(goodsList);
            bloBs.setOrderStatus(1);
            //默认30分钟后关闭
            bloBs.setCloseTime(System.currentTimeMillis() + 1800000L);
            bloBs.setShopId(1L);
            //实付款金额
            bloBs.setOrderPrice(totalPrice + cash.getDeliveryCash());
            bloBs.setProperties(PropertiesUtil.addProperties(request.getProperties()));
            //
            int i = orderMapper.insertSelective(bloBs);
            if (i > 0) {
                //添加订单明细
                OrderDetailWithBLOBs bloBs1 = new OrderDetailWithBLOBs();
                bloBs1.setProperties(PropertiesUtil.addProperties(request.getProperties()));
                bloBs1.setOrderName(sb.toString());
                bloBs1.setShopId(1L);
                bloBs1.setPayType(request.getPayType());
                bloBs1.setUid(request.getUid());
                bloBs1.setAddrId(request.getAddrId());
                bloBs1.setRealPrice(totalPrice + cash.getDeliveryCash());
                bloBs1.setDeliveryCash(cash.getDeliveryCash());
                bloBs1.setDeliveryTime(request.getDeliveryTime());
                bloBs1.setGoodsList(goodsList);
                bloBs1.setNeedInvoice(request.getNeedInvoice());
                bloBs1.setInvoiceDesc(PropertiesUtil.addProperties(request.getInvoiceDesc()));
                bloBs1.setOrderPrice(totalPrice + cash.getDeliveryCash());
                bloBs1.setOrderNum(orderNum);
                bloBs1.setOrderStatus(1);
                bloBs1.setPayStatus(1);
                //添加明细详情
                int i1 = orderDetailMapper.insertSelective(bloBs1);
                if (i1 > 0) {
                    //add rabbit mq and cache
                    OrderMq orderMq = new OrderMq(1, orderNum);
                    // 30min
                    messageProvider.sendMessage(JSON.toJSONString(orderMq),
                            QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                            QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                            1800000L);
                    //如果从购物车下单的话  清空这部分商品
                    String s = "";
                    for (GoodsOrder order : request.getGoodsList()) {
                        if (order.getCart_id() != null) {
                            s = userService.deleteFromCart(order.getCart_id(), request.getUid());
                        }
                        //减少相关的库存
                        String s1 = goodsExtensionService.updateStockNum(order.getS_goods_sn(), 2, order.getGoods_num());
                        log.info("locked {} stock num={}", order.getS_goods_sn(), order.getGoods_num());
                    }
                    log.info("insert order success delete cart={}", s);
                } else {
                    //添加失败-补偿事务 回滚
                    int i2 = orderMapper.deleteByPrimaryKey(bloBs.getId());
                    log.info("insert s_order_detail fail roll back={}", i2);
                }
                return ResultMsgUtil.dmlResult(i1) + "-" + orderNum;
            }
        } finally {
            reentrantLock.unlock();
        }
        return ResultMsg.HANDLER_FAILURE;
    }

    /**
     * 根据订单编号 查询单订单详情
     *
     * @param orderNum
     */
    @Override
    public OrderDto getOrderById(String orderNum, Long uid) {
        //
        OrderDto hget = jedisCache.hget(ORDER_KEY, orderNum, OrderDto.class);
        if (hget == null) {
            hget = orderMapper.selectByOrderNum(orderNum, uid);
            List<OrderGoods> orders = JSON.parseArray(hget.getGoodsListJson(), OrderGoods.class);
            if (orders != null && !orders.isEmpty()) {
                Map<String, List<OrderGoods>> collect = orders.stream().collect(Collectors.groupingBy(OrderGoods::getSupplyName));
                hget.setGoodsList(collect);
            }
            jedisCache.hset(ORDER_KEY, orderNum, hget);
        }
        return hget;
    }

    /**
     * 根据订单编号 查询订单明细详情
     *
     * @param orderNum
     */
    @Override
    public OrderDetailDto getOrderDetailById(String orderNum, Long uid) {
        //
        OrderDetailDto hget = jedisCache.hget(ORDER_DETAIL_KEY, orderNum, OrderDetailDto.class);
        if (hget == null) {
            hget = orderDetailMapper.selectByOrderDetailNum(orderNum, uid);
            if (hget != null) {
                List<OrderGoods> orders = JSON.parseArray(hget.getGoodsListJson(), OrderGoods.class);
                if (orders != null && !orders.isEmpty()) {
                    Map<String, List<OrderGoods>> collect = orders.stream().collect(Collectors.groupingBy(OrderGoods::getSupplyName));
                    hget.setGoodsList(collect);
                }
                hget.setAddress(userService.getDetailById(hget.getUid(), hget.getAddrId()));
                jedisCache.hset(ORDER_DETAIL_KEY, orderNum, hget);
            }
        }
        return hget;
    }

    /**
     * 修改订单状态 级联修改为
     * 1、订单状态：order_status=
     * 1、待付款 = 等待买家付款
     * 2、待发货 = 等待卖家发货
     * 3、待收货 = 等待买家确认
     * 4、已完成 = 交易成功
     * 5、已关闭 = 交易关闭
     * 2、订单明细 支付状态 ：pay_status = '1=未付款 2=已支付 3=已退款
     * pay_type:1=微信 2=支付宝 3=网银 4=货到付款
     * <p>
     * orderNum 订单编号
     * status  要修改的状态
     * payType 支付方式 1=支付宝 2=微信 3=网银 4=货到付款
     */
    @Override
    public String updateOrderStatus(OrderDetailWithBLOBs bloBs) {
        try {
            reentrantLock.lock();
            String orderNum = bloBs.getOrderNum();
            OrderDto orderDto = orderMapper.selectByOrderNum(orderNum, null);
            if (orderDto == null) {
                return ResultMsg.ORDER_NOT_EXIST;
            }
            if (bloBs.getOrderStatus() != null) {
                if (bloBs.getOrderStatus() == 5) {
                    //如果为取消的话 那么恢复库存
                    orderDto = getOrderById(orderNum, null);
                    Map<String, List<OrderGoods>> listMap = orderDto.getGoodsList();
                    if (listMap != null && !listMap.isEmpty()) {
                        listMap.forEach((k, v) -> {
                            v.forEach(x -> {
                                //恢复库存
                                String s1 = goodsExtensionService.updateStockNum(x.getSGoodsSn(), 1, x.getGoodsNum());
                                log.info("incre {} goods stock num={}", x.getSGoodsSn(), s1);
                            });
                        });
                    }
                }
            }
            OrderWithBLOBs orderWithBLOBs = new OrderWithBLOBs();
            if (bloBs.getUid() != null) {
                orderWithBLOBs.setUid(bloBs.getUid());
            }
            orderWithBLOBs.setOrderNum(orderNum);
            orderWithBLOBs.setOrderStatus(bloBs.getOrderStatus());
            orderWithBLOBs.setDeleteFlag(bloBs.getDeleteFlag());
            int i = orderMapper.updateByOrderNum(orderWithBLOBs);
            if (i > 0) {
                jedisCache.hdel(ORDER_KEY, orderNum);
                int i1 = orderDetailMapper.updateDetailByOrderNum(bloBs);
                if (i1 > 0) {
                    jedisCache.hdel(ORDER_DETAIL_KEY, orderNum);
                    return ResultMsg.HANDLER_SUCCESS;
                }
            }
            return ResultMsg.HANDLER_FAILURE;
        } finally {
            reentrantLock.unlock();
        }
    }


    public PagedList<OrderDetailDto> getAllOrders(OrderSelect orderSelect) {
        Page<Object> objects = null;
        List<OrderDetailDto> returnList = new ArrayList<>();
        if (orderSelect.getPageNum() != null && orderSelect.getPageSize() != null) {
            objects = PageHelper.startPage(orderSelect.getPageNum(), orderSelect.getPageSize());
        } else {
            orderSelect.setPageNum(0);
            orderSelect.setPageSize(0);
        }
        List<String> numLists = orderDetailMapper.getOrderNumLists(orderSelect);
        if (numLists != null && !numLists.isEmpty()) {
            for (String orderNum : numLists) {
                OrderDetailDto byId = getOrderDetailById(orderNum, orderSelect.getUid());
                returnList.add(byId);
            }
        }
        PagedList<OrderDetailDto> pagedList = new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), orderSelect.getPageNum(), orderSelect.getPageSize());
        return pagedList;
    }
}
