package com.cj.shop.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/14
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/rest/mall/")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
