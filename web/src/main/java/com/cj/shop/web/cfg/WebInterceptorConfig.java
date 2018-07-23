package com.cj.shop.web.cfg;

import com.cj.shop.service.impl.IpAddressService;
import com.cj.shop.web.intercep.IpAllowInterceptor;
import com.cj.shop.web.intercep.OrderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/27
 * @since 1.0
 */
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private IpAddressService service;

    //增加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IpAllowInterceptor(service))
                .addPathPatterns("/v1/mall/manage/**").excludePathPatterns("/v1/mall/manage/ip/allow/_add")
        .excludePathPatterns("/v1/mall/manage/ip/allow/_update")
        .excludePathPatterns("/v1/mall/manage/ip/allow/_list");
        registry.addInterceptor(new OrderInterceptor())    //指定拦截器类
                .addPathPatterns("/v1/mall/json/user/submitOrder")
                .addPathPatterns("/v1/mall/manage/goods/addGoods");        //指定该类拦截的url


    }
}
