package com.cj.scores.service.impl;

import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.InsertScoresLog;
import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.UserScores;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.cfg.JedisCache;
import com.cj.scores.service.consts.ResultConsts;
import com.cj.scores.service.util.ResultUtil;
import com.cj.scores.service.util.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Service
@Transactional
@Slf4j
public class ScoreService {
    @Autowired
    private ScoresMapper scoresMapper;
    @Autowired
    private JedisCache jedisCache;
    private static final String JEDIS_PREFIX = "cj_scores:user:";

    public Result updateUserScores(UserScoresRequest scores) {
        Result result = null;
        String key = String.valueOf(scores.getUid());
        boolean getLock = jedisCache.tryGetDistributedLock(key, "1", 15);
        try {
            if (getLock) {
                int var1 = 0;
                Integer type = scores.getType();
                Double fromScores = 0.0;
                UserScores userScores = scoresMapper.selectScoresById(scores.getUid());
                if (userScores == null) {
                    //insert
                    if (1 == type) {
                        scores.setScores(scores.getChangeScores());
                        scores.setTotalScores(scores.getChangeScores());
                    } else {
                        return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                    }
                    scores.setEnabled(1);
                    var1 = scoresMapper.insertUserScores(scores);
                } else {
                    //update
                    if (type != 1) {
                        if (scores.getChangeScores() > userScores.getScores()) {
                            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                        }
                        scores.setScores(userScores.getScores() - scores.getChangeScores());
                        if (3 == type) {
                            //lock-score
                            scores.setLockScores(userScores.getLockScores() + scores.getChangeScores());
                        }
                    } else {
                        //incre
                        scores.setTotalScores(userScores.getTotalScores() + scores.getChangeScores());
                        scores.setScores(userScores.getScores() + scores.getChangeScores());
                    }
                    fromScores = userScores.getScores();
                    scores.setVersion(userScores.getVersion());
                    var1 = scoresMapper.updateUserScores(scores);
                }
                result = ResultUtil.getDmlResult(var1);
                Double nowScore = scores.getScores();
                log.info("insert scores result={}", result);
                InsertScoresLog log2 = new InsertScoresLog();
                log2.setTableName(ValidatorUtil.getPaylogTableNameByUid(scores.getUid()));
                log2.setChangeScores(scores.getChangeScores());
                log2.setComment(scores.getComment());
                log2.setScores(nowScore == null ? scores.getChangeScores() : nowScore);
                log2.setFromScores(fromScores);
                log2.setSrcId(scores.getSrcId());
                log2.setType(scores.getType());
                log2.setUid(scores.getUid());
                log2.setSrc(scores.getSrc());
                log2.setId(UUID.randomUUID().toString());
                int insertScoresLog = scoresMapper.insertScoresLog(log2);
                log.info("insertScoresLog result={}", insertScoresLog > 0);
            } else {
                //未获得锁 返回重试信息
                result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR, ResultConsts.ERR_SERVER_MSG);
            }
        } finally {
            if (getLock) jedisCache.releaseDistributedLock(key, "1");
        }
        return result;
    }


    public UserScores getUserScoreByUid(Long uid) {
        String key = JEDIS_PREFIX + uid;
        UserScores hget = jedisCache.hget(key, String.valueOf(uid), UserScores.class);
        if (hget == null) {
            hget = scoresMapper.selectScoresById(uid);
            if (hget != null) {
                jedisCache.hset(key, String.valueOf(uid), hget);
            }
        }
        return hget;
    }


    public List<UserScoreLogDto> getScoreLogList(Long uid, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<UserScoreLogDto> scoreLogDetail = scoresMapper.getScoreLogDetail(ValidatorUtil.getPaylogTableNameByUid(uid), uid);
        scoreLogDetail.forEach(x -> {
            x.setTypeName(getTypeName(x.getType()));
        });
        return scoreLogDetail;
    }


    private String getTypeName(int type) {
        switch (type) {
            case 1:
                return "收入";
            case 2:
                return "支出";
            case 3:
                return "冻结";
        }
        return "其它类型";
    }


}
