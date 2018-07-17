package com.cj.shop.web.controller;

import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
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
    Result errorFast(HttpServletRequest req, Exception e) {
        Result res = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.DUPLICATED_MOTIVE);
        res.setData(ResultConsts.DUPLICATED_SUBMIT);
        return res;
    }

}
