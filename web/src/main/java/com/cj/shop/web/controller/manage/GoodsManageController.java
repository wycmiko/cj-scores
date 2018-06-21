package com.cj.shop.web.controller.manage;

import com.cj.shop.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理 - 商品管理 Rest服务
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/14
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/v1/mall/manage/goods")
public class GoodsManageController {
    @Autowired
    private TokenValidator tokenValidator;
}
