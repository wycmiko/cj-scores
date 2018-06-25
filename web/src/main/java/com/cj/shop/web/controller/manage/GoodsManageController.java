package com.cj.shop.web.controller.manage;

import com.cj.shop.api.entity.GoodsSupply;
import com.cj.shop.api.param.GoodsSupplyRequest;
import com.cj.shop.api.response.PagedList;
import com.cj.shop.service.impl.GoodsService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.ResultUtil;
import com.cj.shop.web.validator.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/25
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping({"/v1/mall/manage/goods/", "/v1/mall/json/goods/"})
public class GoodsManageController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询供应商详情
     *
     * @param id
     * @return
     */
    @GetMapping("/supply/{id}")
    public Result getGoodsSupplyDetail(@PathVariable Long id) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(id)) {
                return CommandValidator.paramEmptyResult();
            }
            log.info("getGoodsSupplyDetail begin");
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            GoodsSupply supplyDetail = goodsService.getSupplyDetail(id);
            result.setData(supplyDetail);
            log.info("getGoodsSupplyDetail end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getGoodsSupplyDetail error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }


    /**
     * 查询供应商列表
     *
     * @return
     */
    @GetMapping("/supplyList")
    public Result getGoodsSupplyList(Integer page_num, Integer page_size, String supply_name, String type) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(type)) {
                return CommandValidator.paramEmptyResult();
            }
            if (!StringUtils.isBlank(supply_name)) {
                supply_name = URLDecoder.decode(supply_name, "UTF-8");
            }
            log.info("supplyList begin supplyName={}", supply_name);
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            PagedList<GoodsSupply> allSupplies = goodsService.findAllSupplies(supply_name, page_num, page_size, type);
            result.setData(allSupplies);
            log.info("supplyList end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("supplyList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 添加供应商
     *
     * @return
     */
    @PostMapping("/addSupply")
    public Result addSupply(@RequestBody GoodsSupplyRequest goodsSupply) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(goodsSupply.getSupplyName())) {
                return CommandValidator.paramEmptyResult();
            }
            log.info("addSupply begin");
            String s = goodsService.insertSupply(goodsSupply);
            log.info("addSupply end");
            result = ResultUtil.getVaildResult(s, result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addSupply error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 修改供应商
     *
     * @return
     */
    @PutMapping("/updateSupply")
    public Result updateSupply(@RequestBody GoodsSupplyRequest goodsSupply) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(goodsSupply.getId(), goodsSupply.getSupplyName())) {
                return CommandValidator.paramEmptyResult();
            }
            log.info("updateSupply begin");
            String s = goodsService.updateSupply(goodsSupply);
            result = ResultUtil.getVaildResult(s, result);
            log.info("updateSupply end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateSupply error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
        }
        return result;
    }
}
