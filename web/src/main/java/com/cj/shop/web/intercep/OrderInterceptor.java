package com.cj.shop.web.intercep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 提交订单拦截器
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/27
 * @since 1.0
 */
@Slf4j
public class OrderInterceptor implements HandlerInterceptor {

    //校验该IP是否重复调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        long curr = System.currentTimeMillis();
        String ip = httpServletRequest.getRemoteAddr();
        log.info("pre request, ip={}", ip);
        Object attribute = httpServletRequest.getSession().getAttribute(ip);
        if (attribute != null) {
            long time = Long.parseLong(attribute.toString());
            long cap = (curr - time) / 1000;
            log.info("pre request cap time={}", cap);
            if (cap <= 10) {
                log.info("pre request, ip intercepted");
                httpServletRequest.getRequestDispatcher("/v1/mall/error/submitFast").forward(httpServletRequest, httpServletResponse);
                return false;
            }
        } else {
            log.info("pre request, ip allowed");
            httpServletRequest.getSession().setAttribute(ip, curr);
        }
        httpServletRequest.getSession().setAttribute(ip, curr);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
