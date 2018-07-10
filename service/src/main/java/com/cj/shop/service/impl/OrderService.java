package com.cj.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cj.shop.api.entity.ExpressCash;
import com.cj.shop.api.entity.OrderDetailWithBLOBs;
import com.cj.shop.api.entity.OrderGoods;
import com.cj.shop.api.entity.OrderWithBLOBs;
import com.cj.shop.api.interf.OrderApi;
import com.cj.shop.api.param.OrderRequest;
import com.cj.shop.api.response.GoodsOrder;
import com.cj.shop.api.response.dto.GoodsStockDto;
import com.cj.shop.common.consts.QueueEnum;
import com.cj.shop.dao.mapper.GoodsStockMapper;
import com.cj.shop.dao.mapper.OrderDetailMapper;
import com.cj.shop.dao.mapper.OrderMapper;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.provider.MessageProvider;
import com.cj.shop.service.util.NumberUtil;
import com.cj.shop.service.util.PropertiesUtil;
import com.cj.shop.service.util.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单服务层
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/10
 * @since 1.0
 */
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
            List<GoodsOrder> goods_list = JSON.parseArray(request.getGoodsList().get("goods_list").toString(), GoodsOrder.class);
            reentrantLock.lock();
            List<OrderGoods> list = new ArrayList<>();
            double totalPrice = 0.0;
            for (GoodsOrder order : goods_list) {
                OrderGoods orderGoods = new OrderGoods();
                GoodsStockDto stockDto = goodsStockMapper.selectBySgoodId(order.getS_goods_sn());
                if (stockDto == null) {
                    return ResultMsg.STOCK_NOT_EXISTS;
                }
                //1、校验库存
                if (order.getGoods_num() > stockDto.getStockNum()) {
                    return ResultMsg.NOT_ENOUGH;
                }
                orderGoods.setSupplyId(stockDto.getSupplyId());
                orderGoods.setGoodsId(stockDto.getGoodsId());
                orderGoods.setGoodsName(stockDto.getGoodsName());
                orderGoods.setGoodsNum(order.getGoods_num());
                orderGoods.setGoodsPrice(stockDto.getSellPrice());
                orderGoods.setPreviewImg(stockDto.getPreviewImg());
                orderGoods.setSGoodsSn(stockDto.getSGoodsSn());
                orderGoods.setGoodsSn(stockDto.getGoodsSn());
                orderGoods.setSpecList(stockDto.getSpecList());
                orderGoods.setSupplyName(stockDto.getSupplyName());
                list.add(orderGoods);
                totalPrice += stockDto.getSellPrice() * order.getGoods_num();
            }
            OrderWithBLOBs bloBs = new OrderWithBLOBs();
            BeanUtils.copyProperties(request, bloBs);
            //订单编号
            String orderNum = NumberUtil.getOrderIdByUUId();
            //全局运费
            ExpressCash cash = goodsExtensionService.getExpressCash();
            bloBs.setOrderNum(orderNum);
            bloBs.setDeliveryCash(cash.getDeliveryCash());
            bloBs.setDeliveryProtect(0);
            log.info("uid={}, submit order price={}", request.getUid(), totalPrice);
            //实付款金额
            bloBs.setOrderPrice(totalPrice);
            String goodsList = JSON.toJSONString(list);
            bloBs.setGoodsList(goodsList);
            bloBs.setOrderStatus(1);
            //默认30分钟后关闭
            bloBs.setCloseTime(System.currentTimeMillis() + 1800000L);
            bloBs.setShopId(1L);
            bloBs.setProperties(PropertiesUtil.addProperties(request.getProperties()));
            //
            int i = orderMapper.insertSelective(bloBs);
            if (i > 0) {
                //添加订单明细
                OrderDetailWithBLOBs bloBs1 = new OrderDetailWithBLOBs();
                bloBs1.setProperties(PropertiesUtil.addProperties(request.getProperties()));
                bloBs1.setShopId(1L);
                bloBs1.setUid(request.getUid());
                bloBs1.setAddrId(request.getAddrId());
                bloBs1.setRealPrice(totalPrice);
                bloBs1.setDeliveryCash(cash.getDeliveryCash());
                bloBs1.setDeliveryTime(request.getDeliveryTime());
                bloBs1.setGoodsList(goodsList);
                bloBs1.setNeedInvoice(request.getNeedInvoice());
                bloBs1.setInvoiceDesc(PropertiesUtil.addProperties(request.getInvoiceDesc()));
                bloBs1.setOrderNum(orderNum);
                bloBs1.setOrderStatus(1);
                bloBs1.setPayStatus(1);
                //添加明细详情
                int i1 = orderDetailMapper.insertSelective(bloBs1);
                if (i1 > 0) {
                    //add rabbit mq and cache
                    // 30min
                    messageProvider.sendMessage(orderNum,
                            QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                            QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                            1800000L);
                } else {
                    //添加失败 回滚
                    int i2 = orderMapper.deleteByPrimaryKey(bloBs.getId());
                    log.info("insert s_order_detail fail roll back={}", i2);
                }
                return ResultMsgUtil.dmlResult(i1);
            }
        } finally {
            reentrantLock.unlock();
        }
        return ResultMsg.HANDLER_FAILURE;
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param status
     */
    @Override
    public String updateOrderStatus(String orderId, String status) {
        return null;
    }
}
