package com.cj.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cj.shop.api.entity.OrderDetailWithBLOBs;
import com.cj.shop.api.entity.OrderMq;
import com.cj.shop.api.entity.Tracess;
import com.cj.shop.api.response.dto.ExpressTraceDto;
import com.cj.shop.api.response.dto.OrderDetailDto;
import com.cj.shop.common.consts.QueueEnum;
import com.cj.shop.common.utils.HttpClientUtils;
import com.cj.shop.service.cfg.ExpressConfig;
import com.cj.shop.service.cfg.JedisCache;
import com.cj.shop.service.consts.ResultMsg;
import com.cj.shop.service.provider.MessageProvider;
import com.cj.shop.service.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/12
 * @since 1.0
 */
@Service
@Slf4j
@Transactional
public class ExpressService {
    @Autowired
    private ExpressConfig expressConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private JedisCache jedisCache;
    public static final String EXPRESS_KEY = "cj_shop:mall:express:";

    /**
     * 查询物流公司
     */
    public Map<String, Object> getExpressCompany() {
        return expressConfig.map;
    }

    /**
     * 订单发货：
     * 添加 /修改 运单号
     */
    public String addExpressNum(String orderNum, String expressNo, String expressName) {
        OrderDetailDto detailById = orderService.getOrderDetailById(orderNum, null);
        if (detailById == null) {
            return ResultMsg.ORDER_NOT_EXIST;
        }
        if (2 == detailById.getOrderStatus() || 3 == detailById.getOrderStatus()) {
            //如果为待发货 和待收货可修改 其余状态不可修改
            OrderDetailWithBLOBs bloBs = new OrderDetailWithBLOBs();
            bloBs.setOrderNum(orderNum);
            bloBs.setExpressId(expressNo);
            bloBs.setExpressName(expressName);
            bloBs.setOrderStatus(3);
            Object auto_recei_time = detailById.getProperty("auto_confirm_time");
            if (auto_recei_time == null) {
                Map map = new HashMap();
                //保存一个自动确认收货时间
                map.put("auto_confirm_time", System.currentTimeMillis() + 1296000000L);
                bloBs.setProperties(PropertiesUtil.addProperties(map));
                //add Rabbit MQ once
                OrderMq mq = new OrderMq();
                mq.setOrderNum(orderNum);
                mq.setType(2);
                // 15days 自动确认收货  这里为了测试 时间缩短 900000L s
                messageProvider.sendMessage(JSON.toJSONString(mq),
                        QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                        QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                        900000L);
            }
            return orderService.updateOrderStatus(bloBs);
        } else {
            return ResultMsg.EXPRESS_CANNOT_UPDATE;
        }
    }


    /**
     * 查询物流动态
     */
    public Map<String, Object> getTraces(String orderNum, Long uid) throws Exception {
        String key = EXPRESS_KEY + orderNum;
        ExpressTraceDto traceDto = jedisCache.get(key, ExpressTraceDto.class);
        Map<String,Object> returnMap = new HashMap<>();
        List<Tracess> list = new ArrayList<>();
        if (traceDto != null && traceDto.getTraces() != null) {
            log.info("get express info by cache");
            list.addAll(traceDto.getTraces());
        } else {
            OrderDetailDto detailById = orderService.getOrderDetailById(orderNum, uid);
            if (detailById != null) {
                if (detailById.getOrderStatus() == 3 || detailById.getOrderStatus() == 4) {
                    String expressId = detailById.getExpressId();
                    String expressName = detailById.getExpressName();
                    log.info("userid={}, app-secret={} map-size={}", expressConfig.getUserId(), expressConfig.getApiKey(), expressConfig.map);
                    String url = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
                    Map<String, Object> map = new HashMap<>();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("OrderCode", "");
                    jsonObject.put("ShipperCode", expressConfig.map.get(expressName));   //快递公司编码
                    jsonObject.put("LogisticCode", expressId);  //快递单号
                    map.put("DataType", "2-json");
                    map.put("RequestData", jsonObject.toJSONString());
                    map.put("EBusinessID", expressConfig.getUserId());
                    map.put("RequestType", "1002");
                    map.put("DataSign", expressConfig.getDataSign(jsonObject.toJSONString()));
                    String s = HttpClientUtils.httpAsyncPostFormData(url, map);
                    log.info("http post express info={}", s);
                    traceDto = JSON.parseObject(s, ExpressTraceDto.class);
                    if (traceDto != null && traceDto.getTraces() != null) {
                        traceDto.setExpressName(expressName);
                        traceDto.setOrderNum(orderNum);
                        jedisCache.set(key, traceDto, 7200);
                        list.addAll(traceDto.getTraces());
                    }
                }
            }
        }
        if (traceDto != null) {
            returnMap.put("expressName", traceDto.getExpressName());
            returnMap.put("orderNum", traceDto.getOrderNum());
            returnMap.put("expressId", traceDto.getLogisticCode());
            returnMap.put("trace", list);
        }
        return returnMap;

    }

}
