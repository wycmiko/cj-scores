package com.cj.shop.web.controller.manage;

import com.alibaba.fastjson.JSONObject;
import com.cj.shop.api.entity.GoodsType;
import com.cj.shop.api.param.GoodsTypeRequest;
import com.cj.shop.service.impl.GoodsTypeService;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.ResultUtil;
import com.cj.shop.web.validator.CommandValidator;
import com.cj.shop.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台管理 - 商品类型管理 Rest服务
 * 接口文档api: http://172.28.3.45:3008/project/15/interface/api  目录地址：商城后台-商品*管理
 * @author yuchuanWeng
 * @date 2018/6/14
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping({"/v1/mall/manage/goods/type"})
public class GoodsTypeManageController {
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private GoodsTypeService goodsTypeService;


    /**
     * 查询全部类型
     *
     * @return
     */
    @GetMapping("/getTypeList")
    public Result getTypeList(String type, Integer page_num, Integer page_size) {
        //token校验
        Result result = null;
        try {
            if (CommandValidator.isEmpty(type)) {
            return CommandValidator.paramEmptyResult();
        }
            log.info("getTypeList");
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            result.setData(goodsTypeService.getAllGoodsType(type, page_num, page_size));
            log.info("getTypeList end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getTypeList error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG+e.getMessage());
        }
        return result;
    }

    /**
     * 查询单类型详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getGoodsTypeDetail(@PathVariable Long id, String type) {
        //token校验
        Result result = null;
        try {
            log.info("getGoodsTypeDetail begin id={}", id);
            if (CommandValidator.isEmpty(id, type)) {
                return CommandValidator.paramEmptyResult();
            }
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG);
            GoodsType typeById = goodsTypeService.getGoodsTypeById(id, type);
            result.setData(typeById == null ? new JSONObject() : typeById);
            log.info("getGoodsTypeDetail end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getGoodsTypeDetail error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG+e.getMessage());
        }
        return result;
    }


    /**
     * 添加类型详情
     *
     * @return
     */
    @PostMapping("/addType")
    public Result addType(@RequestBody GoodsTypeRequest request) {
        //token校验
        Result result = null;
        try {
            log.info("addType begin");
            if (CommandValidator.isEmpty(request.getTypeName())) {
                return CommandValidator.paramEmptyResult();
            }
            String s = goodsTypeService.addGoodsType(request);
            result = ResultUtil.getVaildResult(s, result);
            log.info("addType end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addType error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG+e.getMessage());
        }
        return result;
    }

    /**
     * 修改类型
     *
     * @return
     */
    @PutMapping("/updateType")
    public Result updateType(@RequestBody GoodsTypeRequest request) {
        //token校验
        Result result = null;
        try {
            log.info("updateType begin");
            if (CommandValidator.isEmpty(request.getId())) {
                return CommandValidator.paramEmptyResult();
            }
            String s = goodsTypeService.updateGoodsType(request);
            result = ResultUtil.getVaildResult(s, result);
            log.info("updateType end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateType error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG+e.getMessage());
        }
        return result;
    }

    /**
     * 删除类型
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteType(@PathVariable Long id, Integer type) {
        //token校验
        Result result = null;
        try {
            log.info("deleteType begin");
            if (CommandValidator.isEmpty(id, type)) {
                return CommandValidator.paramEmptyResult();
            }
            String s = goodsTypeService.deleteGoodsType(id, type);
            result = ResultUtil.getVaildResult(s, result);
            log.info("deleteType end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteType error {}", e.getMessage());
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR);
            result.setData(ResultConsts.ERR_SERVER_MSG+e.getMessage());
        }
        return result;
    }
}
