package com.cj.shop.web.controller;

import com.cj.shop.web.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Create Time: 2018年03月21日</p>
 * <p>@author tangxd</p>
 **/
@RequestMapping("/v1/match")
@Slf4j
@RestController
public class GlobalController {


    @RequestMapping("/error/comment")
    Result errorFast(HttpServletRequest req) {
        Result res = new Result("1","error");
        res.setData("错误！");
        return res;
    }

}
