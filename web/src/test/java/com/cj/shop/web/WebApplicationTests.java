package com.cj.shop.web;

import com.cj.shop.api.entity.OrderGoods;
import com.cj.shop.api.param.PayLogRequest;
import com.cj.shop.api.param.select.GoodsSelect;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.api.response.dto.GoodsDto;
import com.cj.shop.common.consts.QueueEnum;
import com.cj.shop.common.utils.DateUtils;
import com.cj.shop.service.cfg.ExpressConfig;
import com.cj.shop.service.impl.GoodsService;
import com.cj.shop.service.impl.PayService;
import com.cj.shop.service.provider.MessageProvider;
import com.cj.shop.web.utils.UcUtil;
import com.github.crab2died.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    UcUtil tokenValidator;

    @Test
    public void test() throws Exception {
        int i = 1;
        for (; i < 10; i++) {
            System.out.println("第" + i + "次调用 uid=" + tokenValidator.isExistUser("18879881009"));
        }
    }

    @Autowired
    private GoodsService goodsService;

    @Test
    public void testObject2Excel() throws Exception {
        GoodsSelect select = new GoodsSelect();
        select.setType("all");
        PagedList<GoodsDto> allGoods = goodsService.getAllGoods(select);
        ExcelUtils.getInstance().exportObjects2Excel(allGoods.getList(), GoodsDto.class, true, "商品列表", true, "D:/B.xlsx");
    }



    @Autowired
    private MessageProvider messageProvider;

    /**
     * 测试延迟消息消费
     */
    @Test
    public void testLazy() {
        // 测试延迟10秒
        messageProvider.sendMessage("测试延迟消费,写入时间：" + DateUtils.getCommonString(),
                QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                10000);
    }

    @Test
    public void testGroupMap(){
        List<OrderGoods> list = new ArrayList<>();
        OrderGoods o1 = new OrderGoods();
        o1.setSupplyName("京东自营");
        o1.setGoodsName("iphone6");
        OrderGoods o2 = new OrderGoods();
        o2.setSupplyName("京东自营");
        o2.setGoodsName("iphone7");
        OrderGoods o3 = new OrderGoods();
        o3.setSupplyName("苏宁易购");
        o3.setGoodsName("iphoneSE");
        OrderGoods o4 = new OrderGoods();
        o4.setSupplyName("唯品会");
        o4.setGoodsName("iphoneX");
        list.add(o1);
        list.add(o2);
        list.add(o3);
        list.add(o4);
        Map<String, List<OrderGoods>> collect = list.stream().collect(Collectors.groupingBy(OrderGoods::getSupplyName));
        collect.forEach((k,v) ->{
            log.info("key={} value={}", k, v);
        });
    }


    @Autowired
    private PayService payService;

    @Test
    public void testInserPayLog(){
        PayLogRequest request = new PayLogRequest();
        request.setOrderNum("2018071118263342550000001");
        request.setPlatTradeNo("test1");
        request.setTradeNo("test1");
        request.setUid(478L);
        //支付成功
        request.setPayStatus(1);
        //
        request.setPayTime(DateUtils.getCommonString());
        request.setTotalPrice(9888.25);
        request.setPayType(1);
        payService.insertPayLog(request);
    }

    @Autowired
    private ExpressConfig expressConfig;
    @Test
    public void testProp(){
        log.info("userid={}, app-secret={} map-size={}", expressConfig.getUserId(), expressConfig.getApiKey(), expressConfig.map);

    }

}
