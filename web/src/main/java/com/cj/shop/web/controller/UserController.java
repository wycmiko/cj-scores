package com.cj.shop.web.controller;

import com.cj.shop.service.impl.UserService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/14
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/v1/mall/json/user")
public class UserController {
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private UserService userService;

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/address/{id}")
    public Result getAddressDetail(@PathVariable Long id, String token) {
        //token校验
        Result result = null;
        try {
            if (!tokenValidator.checkToken(token)) {
                log.info("EnrollerController.getMyInfo 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(userService.getDetailById(id));
        } catch (Exception e) {
            log.error("getAddressDetail error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.RESPONSE_FAILURE_MSG);
        }
        return result;
    }
}
