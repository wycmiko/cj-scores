package com.cj.scores.service.impl;

import com.alibaba.fastjson.JSON;
import com.cj.scores.api.consts.ResultConsts;
import com.cj.scores.api.consts.ScoreTypeEnum;
import com.cj.scores.api.consts.SrcEnum;
import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.InsertScoresLog;
import com.cj.scores.api.pojo.PagedList;
import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.UserScores;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.api.pojo.select.ScoreSelect;
import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.cfg.JedisCache;
import com.cj.scores.service.util.ResultUtil;
import com.cj.scores.service.util.ValidatorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
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
    private static final String JEDIS_PREFIX_LOCK = JEDIS_PREFIX + "lock:";

    public Result updateUserScoresGrpc(@Valid UserScoresRequest request) {
        if (ValidatorUtil.isEmpty(request.getSrcId(), request.getType(), request.getChangeScores(), request.getUid())
                || SrcEnum.getTypeName(request.getSrcId()) == null) {
            return ResultUtil.paramNullResult();
        }
        return updateUserScores(request);
    }

    public Result getUserScoreByUidGrpc(long uid) {
        Result result = null;
        try {
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, JSON.toJSONString(getUserScoreByUid(uid)));
        } catch (Exception e) {
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR, e.getMessage());
        }
        return result;
    }

    public Result getUserScoreLogByUidGrpc(long uid, Integer pageNum, Integer pageSize) {
        Result result = null;
        try {
            if (ValidatorUtil.isEmpty(pageNum, pageSize)) {
                return ResultUtil.paramNullResult();
            }
            result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.RESPONSE_SUCCEED_MSG, JSON.toJSONString(getScoreLogList(uid, pageNum, pageSize)));
        } catch (Exception e) {
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR, e.getMessage());
        }
        return result;
    }


    public Result updateUserScores(UserScoresRequest scores) {
        Result result = null;
        String key = JEDIS_PREFIX_LOCK + String.valueOf(scores.getUid());
        boolean hasGotLock = jedisCache.tryGetDistributedLock(key, "1", 15);
        try {
            if (hasGotLock) {
                int var1 = 0;
                final Integer type = scores.getType();
                Double fromScores = 0.0;
                UserScores userScores = scoresMapper.selectScoresById(scores.getUid());
                if (userScores == null) {
                    //插入操作
                    if (ScoreTypeEnum.INCOME.getType() != type)
                        return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                    scores.setScores(scores.getChangeScores());
                    scores.setTotalScores(scores.getChangeScores());
                    scores.setEnabled(Boolean.TRUE);
                    var1 = scoresMapper.insertUserScores(scores);
                } else {
                    //更新操作
                    if (type == ScoreTypeEnum.LOCK.getType() || type == ScoreTypeEnum.OUTCOME.getType()) {
                        //锁定或支出
                        if (scores.getChangeScores() > userScores.getScores())
                            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                        scores.setScores(userScores.getScores() - scores.getChangeScores());
                        scores.setLockScores(ScoreTypeEnum.LOCK.getType() == type ? userScores.getLockScores() + scores.getChangeScores() : null);
                    } else if (ScoreTypeEnum.UNLOCK.getType() == type) {
                        //解锁
                        if (scores.getChangeScores() > userScores.getLockScores())
                            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                        scores.setLockScores(userScores.getLockScores() - scores.getChangeScores());
                        scores.setScores(userScores.getScores() + scores.getChangeScores());
                    } else {
                        //收入
                        scores.setTotalScores(userScores.getTotalScores() + scores.getChangeScores());
                        scores.setScores(userScores.getScores() + scores.getChangeScores());
                    }
                    //计算起始分数
                    fromScores = userScores.getScores();
                    scores.setVersion(userScores.getVersion());
                    var1 = scoresMapper.updateUserScores(scores);
                }
                result = ResultUtil.getDmlResult(var1);
                log.info("uid={} update scores result={}", scores.getUid(), result.getMsg());
                if (var1 > 0) {
                    //如未冲突 则更新
                    Double nowScore = scores.getScores();
                    InsertScoresLog log2 = this.setLogObjByScores(scores);
                    log2.setScores(nowScore == null ? scores.getChangeScores() : nowScore);
                    log2.setFromScores(fromScores);
                    int var2 = scoresMapper.insertScoresLog(log2);
                    jedisCache.hdel(JEDIS_PREFIX, String.valueOf(scores.getUid()));
                    log.info("uid = {} insert score log result={}", scores.getUid(), var2 > 0);
                }
            } else {
                //未获得锁 返回重试信息
                result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.SERVER_ERROR, ResultConsts.ERR_SERVER_MSG);
            }
        } finally {
            if (hasGotLock) jedisCache.releaseDistributedLock(key, "1");
        }
        return result;
    }

    public PagedList<UserScores> getUserScoreList(ScoreSelect select) {
        Page<Object> objects = null;
        List<UserScores> returnList = new ArrayList<>();
        Integer pageNum = select.getPage_num();
        Integer pageSize = select.getPage_size();
        if (pageNum != null && pageSize != null) {
            //if need page-limit
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        List<Long> longs = ValidatorUtil.checkNotEmptyList(scoresMapper.selectAllScores(select));
        if (!longs.isEmpty()) {
            longs.forEach(x -> {
                returnList.add(getUserScoreByUid(x));
            });
        }
        return new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
    }


    public UserScores getUserScoreByUid(Long uid) {
        UserScores hget = jedisCache.hget(JEDIS_PREFIX, String.valueOf(uid), UserScores.class);
        if (hget == null) {
            hget = scoresMapper.selectScoresById(uid);
            if (hget != null) {
                jedisCache.hset(JEDIS_PREFIX, String.valueOf(uid), hget);
            }
        }
        return hget;
    }


    public PagedList<UserScoreLogDto> getScoreLogList(Long uid, Integer pageNum, Integer pageSize) {
        Page<Object> objects = null;
        List<UserScoreLogDto> returnList = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        }
        List<UserScoreLogDto> scoreLogDetail = scoresMapper.getScoreLogDetail(ValidatorUtil.getPaylogTableNameByUid(uid), uid);
        scoreLogDetail.forEach(x -> {
            x.setSrc(SrcEnum.getTypeName(x.getSrcId()));
            x.setTypeName(ScoreTypeEnum.getTypeName(x.getType()));
            returnList.add(x);
        });
        return new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
    }


    private InsertScoresLog setLogObjByScores(UserScoresRequest scores) {
        InsertScoresLog log2 = new InsertScoresLog();
        log2.setTableName(ValidatorUtil.getPaylogTableNameByUid(scores.getUid()));
        log2.setChangeScores(scores.getChangeScores());
        log2.setComment(scores.getComment());
        log2.setSrcId(scores.getSrcId());
        log2.setType(scores.getType());
        log2.setUid(scores.getUid());
        log2.setSrc(SrcEnum.getTypeName(scores.getSrcId()));
        log2.setId(UUID.randomUUID().toString());
        return log2;
    }
}
