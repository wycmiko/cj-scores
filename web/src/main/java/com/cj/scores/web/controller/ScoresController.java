package com.cj.scores.web.controller;

import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.service.consts.ResultConsts;
import com.cj.scores.service.impl.ScoreService;
import com.cj.scores.service.util.ResultUtil;
import com.cj.scores.web.validator.CommandValidator;
import com.cj.scores.web.validator.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TokenValidator tokenValidator;

    /**
     * 修改积分
     */
    @PostMapping("/updateUserScore")
    public Result updateUserScore(@Valid @RequestBody UserScoresRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResultUtil.paramNullResult();
        }
        log.info("update user score begin");
        return service.updateUserScores(request);
    }


    /**
     * 根据uid积分
     */
    @GetMapping("/getScoreByUid")
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
}