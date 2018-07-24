package com.cj.shop.web.intercep;

import com.cj.shop.api.entity.IpAllow;
import com.cj.shop.service.impl.IpAddressService;
import com.cj.shop.web.utils.IPAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IP授权校验拦截器
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/27
 * @since 1.0
 */
@Slf4j
public class IpAllowInterceptor implements HandlerInterceptor {

    private IpAddressService ipAddressService;

    public IpAllowInterceptor(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    //校验该IP是否授权
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        String headerIp = httpServletRequest.getHeader("ip-allow");
//        log.info("header ip = {}", headerIp);
        String ip = IPAddressUtil.getIpAddressNotInProxy(httpServletRequest);
        log.info("shop mall manage ip check request, ip={}", ip);
        IpAllow detail = ipAddressService.getIpAllowedDetail(ip, 1);
        if (detail == null) {
            httpServletRequest.getRequestDispatcher("/v1/mall/error/ipDenied").forward(httpServletRequest, httpServletResponse);
            log.info("ip permission denied");
            return false;
        }
        log.info("ip permission success");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
