package com.cj.shop.web.intercep;

import com.cj.shop.service.cfg.JedisCache;
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

    private JedisCache jedisCache;

    public OrderInterceptor(JedisCache cache) {
        this.jedisCache = cache;
    }

    //校验该IP是否重复调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        long curr = System.currentTimeMillis();
        String ip = httpServletRequest.getRemoteAddr();
        Long aLong = jedisCache.get("cj_mall:ip:" + ip, Long.class);
        log.info("pre request, ip={}", ip);
        if (aLong != null) {
            long cap = (curr - aLong) / 1000;
            log.info("pre request cap time={}", cap);
            if (cap <= 8) {
                log.info("pre request, ip intercepted");
                httpServletRequest.getRequestDispatcher("/v1/mall/error/submitFast").forward(httpServletRequest, httpServletResponse);
                return false;
            }
        } else {
            log.info("pre request, ip allowed");
            jedisCache.setByDefaultTime("cj_mall:ip:" + ip, String.valueOf(curr));
        }
        jedisCache.setByDefaultTime("cj_mall:ip:" + ip, String.valueOf(curr));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
