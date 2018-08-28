package com.cj.scores.web.cfg;

import com.cj.scores.api.consts.ResultConsts;
import com.cj.scores.api.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author yuchuanWeng( )
 * @date 2018/4/25
 * @since 1.0
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

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
        log.error("【error】  path: {}:{}{}, errMsg:{}", request.getRemoteAddr(), request.getLocalPort(), request.getRequestURI(), e.getMessage());
        Result result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        result.setData(ResultConsts.ERR_SERVER_MSG + "!--错误信息:" + e.getMessage());
        e.printStackTrace();
        return result;
    }

}
