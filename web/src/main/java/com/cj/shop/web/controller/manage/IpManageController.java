package com.cj.shop.web.controller.manage;

import com.cj.shop.api.entity.IpAllow;
import com.cj.shop.service.impl.IpAddressService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.ResultUtil;
import com.cj.shop.web.validator.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yuchuanWeng
 * @date 2018/7/20
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/v1/mall/manage/ip")
public class IpManageController {
    @Autowired
    private IpAddressService ipAddressService;

    @PostMapping("/allow/_add")
    public Result addIpAllow(@RequestBody IpAllow ipAllow) {
        Result result = null;
        try {
            if (CommandValidator.isEmpty(ipAllow, ipAllow.getIp())) {
                return CommandValidator.paramEmptyResult();
            }
            log.info("Add Ip={} to System", ipAllow.getIp());
            String s = ipAddressService.insertIpAllow(ipAllow);
            result = ResultUtil.getVaildResult(s, result);
            log.info("Add Ip end, result={}", s);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Add Ip error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    @PostMapping("/allow/_update")
    public Result enabledIp(@RequestBody IpAllow ipAllow) {
        Result result = null;
        try {
            if (CommandValidator.isEmpty(ipAllow, ipAllow.getIp(), ipAllow.getEnabled())) {
                return CommandValidator.paramEmptyResult();
            }
            log.info("update Ip={} to System", ipAllow.getIp());
            String s = ipAddressService.updateIpAllow(ipAllow.getIp(), ipAllow.getEnabled());
            result = ResultUtil.getVaildResult(s, result);
            log.info("update Ip end, result={}", s);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("_update Ip error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }

    @GetMapping("/allow/_list")
    public Result ipList() {
        Result result = null;
        try {
            log.info("ipList begin");
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(ipAddressService.getAllIpAllows());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ipList Ip error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG + e.getMessage());
        }
        return result;
    }
}
