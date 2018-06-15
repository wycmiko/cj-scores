package com.cj.shop.web.cfg;

import com.cj.shop.web.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/25
 * @since 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常处理
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Result allExceptionHandler(HttpServletRequest request,
                                      Exception e) throws Exception {
        logger.error("【error】  path: {}:{}{}, errMsg:{}", request.getRemoteAddr(), request.getLocalPort(), request.getRequestURI(), e.getMessage());
        return new Result("1101","error");
    }

}
