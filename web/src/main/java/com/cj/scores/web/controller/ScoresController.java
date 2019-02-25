package com.cj.scores.web.controller;

import com.cj.scores.api.consts.ResultConsts;
import com.cj.scores.api.consts.SrcEnum;
import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.api.pojo.select.ScoreLogSelect;
import com.cj.scores.api.pojo.select.ScoreSelect;
import com.cj.scores.service.impl.CountService;
import com.cj.scores.service.impl.ScoreService;
import com.cj.scores.service.util.ResultUtil;
import com.cj.scores.web.validator.CommandValidator;
import com.cj.scores.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/v1/scores/")
public class ScoresController {
    @Autowired
    private ScoreService service;
    @Autowired
    private CountService countService;
    @Autowired
    private TokenValidator tokenValidator;

    /**
     * 修改积分
     */
    @PostMapping("/json/updateUserScore")
    public Result updateUserScore(@Valid @RequestBody UserScoresRequest request, BindingResult result) throws Exception {
        if (result.hasErrors() || StringUtils.isEmpty(request.getToken())
                || SrcEnum.getTypeName(request.getSrcId()) == null || request.getChangeScores() == null || request.getChangeScores().doubleValue() <= 0) {
            return ResultUtil.paramNullResult();
        }
        if (!tokenValidator.checkToken(request.getToken())) {
            log.info("getScoreByUid【Invaild token!】");
            return tokenValidator.invaildTokenFailedResult();
        }
        long uid = tokenValidator.getUidByToken(request.getToken());
        request.setUid(uid);
        log.info("update user score begin");
        return service.updateUserScores(request);
    }


    /**
     * 修改积分-后台管理
     */
    @PostMapping("/manage/updateUserScore")
    public Result updateUserScoreManage(@Valid @RequestBody UserScoresRequest request, BindingResult result) throws Exception {
        if (result.hasErrors() || request.getUid() == null
                || SrcEnum.getTypeName(request.getSrcId()) == null) {
            return ResultUtil.paramNullResult();
        }
        log.info("updateUserScoreManage begin");
        return service.updateUserScores(request);
    }


    /**
     * 根据uid查询积分
     */
    @GetMapping("/json/getScoreByUid")
    public Result getScoreByUid(String token) throws Exception {
        if (CommandValidator.isEmpty(token)) {
            return ResultUtil.paramNullResult();
        }
        if (!tokenValidator.checkToken(token)) {
            log.info("getScoreByUid【Invaild token!】");
            return tokenValidator.invaildTokenFailedResult();
        }
        long uid = tokenValidator.getUidByToken(token);
        Result result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, service.getUserScoreByUid(uid));
        return result;
    }


    /**
     * 查询用户积分列表
     */
    @GetMapping("/manage/getUserScoreList")
    public Result getUserScoreList(ScoreSelect select) throws Exception {
        Result result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, service.getUserScoreList(select));
        return result;
    }

    /**
     * 根据uid查询收支明细
     */
    @GetMapping("/manage/getScoreLog")
    public Result getScoreLogByUidManage(ScoreLogSelect select) throws Exception {
        log.info("getScoreLogByUidManage");
        if (CommandValidator.isEmpty(select.getUid()))
            return ResultUtil.paramNullResult();
        Result result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, service.getScoreLogList(select));
        return result;
    }


    /**
     * 2018-11-7 new：
     * 查询
     * 1.获取总数量/消耗总数量/当前总数量
     * 2.人平金币数量
     */
    @GetMapping("/manage/data/get")
    public Result getData() throws Exception {
        log.info("getData");
        return new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, countService.getScoreCount());
    }

    @GetMapping("/manage/data/refresh")
    public Result refreshData() throws Exception {
        log.info("refreshData");
        return new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, countService.refreshData());
    }


    /**
     * 根据uid查询收支明细
     */
    @GetMapping("/json/getScoreLogByUid")
    public Result getScoreLogByUid(ScoreLogSelect select) throws Exception {
        String token = select.getToken();
        if (CommandValidator.isEmpty(token))
            return ResultUtil.paramNullResult();
        if (!tokenValidator.checkToken(token)) {
            log.info("getScoreLogByUid【Invaild token!】");
            return tokenValidator.invaildTokenFailedResult();
        }
        long uid = tokenValidator.getUidByToken(token);
        select.setUid(uid);
        Result result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, service.getScoreLogList(select));
        return result;
    }
}
