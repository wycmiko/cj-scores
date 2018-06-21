package com.cj.shop.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.cj.shop.api.entity.UserAddress;
import com.cj.shop.api.param.UserAddressRequest;
import com.cj.shop.service.impl.UserService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.ResultUtil;
import com.cj.shop.web.validator.CommandValidator;
import com.cj.shop.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param id
     * @return
     */
    @GetMapping("/address/{id}")
    public Result getAddressDetail(@PathVariable Long id, String token) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(id, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("getAddressDetail 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            UserAddress detail = userService.getDetailById(tokenValidator.getUidByToken(token), id);
            result.setData(detail == null ? new JSONObject() : detail);
        } catch (Exception e) {
            log.error("getAddressDetail error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询收货地址列表
     *
     * @param token
     * @return
     */
    @GetMapping("/addressList")
    public Result addressList(String token, Integer page_num, Integer page_size) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("addressList 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(userService.getAllAddress(uid, page_num, page_size));
        } catch (Exception e) {
            log.error("addressList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 添加收货地址
     *
     * @param request
     * @return
     */
    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody UserAddressRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getToken())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("addAddress 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            UserAddress address = new UserAddress();
            BeanUtils.copyProperties(request, address);
            address.setUid(uid);
            String s = userService.addAddress(address);
            result = ResultUtil.getVaildResult(s, result);
            log.info("addAddress end");
        } catch (Exception e) {
            log.error("addAddress error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 修改收货地址(设默认)
     *
     * @param request
     * @return
     */
    @PutMapping("/updateAddr")
    public Result updateAddr(@RequestBody UserAddressRequest request) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(request.getId(), request.getToken())) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(request.getToken())) {
                log.info("updateAddr 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(request.getToken());
            UserAddress address = new UserAddress();
            BeanUtils.copyProperties(request, address);
            address.setUid(uid);
            String s = userService.updateAddress(address);
            result = ResultUtil.getVaildResult(s, result);
            log.info("updateAddr end");
        } catch (Exception e) {
            log.error("updateAddr error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除收货地址(设默认)
     *
     * @param token
     * @return
     */
    @DeleteMapping("/deleteAddr/{id}")
    public Result deleteAddr(@PathVariable Long id, String token) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(id, token)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!tokenValidator.checkToken(token)) {
                log.info("deleteAddr 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            long uid = tokenValidator.getUidByToken(token);
            String s = userService.deleteAddress(id, uid);
            result = ResultUtil.getVaildResult(s, result);
            log.info("deleteAddr end");
        } catch (Exception e) {
            log.error("deleteAddr error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }
}
