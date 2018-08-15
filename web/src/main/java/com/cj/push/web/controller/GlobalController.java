package com.cj.push.web.controller;

import com.cj.push.web.dto.Result;
import com.cj.push.web.utils.IPAddressUtil;
import com.cj.push.web.consts.ResultConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Create Time: 2018年03月21日</p>
 * <p>@author tangxd</p>
 **/
@RequestMapping("/v1/mall")
@Slf4j
@RestController
public class GlobalController {


    @RequestMapping("/error/submitFast")
    public Result errorFast() {
        Result res = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.DUPLICATED_MOTIVE);
        res.setData(ResultConsts.DUPLICATED_SUBMIT);
        return res;
    }

    @RequestMapping("/error/ipDenied")
    public Result ipDenied(HttpServletRequest request) {
        Result res = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.IP_FAILURE_MSG);
        res.setData(ResultConsts.IP_NOT_ALLOWED + ",你的IP:" + IPAddressUtil.getIpAddressNotInProxy(request));
        return res;
    }

}
