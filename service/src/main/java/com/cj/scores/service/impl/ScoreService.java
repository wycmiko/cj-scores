package com.cj.scores.service.impl;

import com.cj.scores.api.ScoresApi;
import com.cj.scores.api.consts.ResultConsts;
import com.cj.scores.api.consts.ScoreTypeEnum;
import com.cj.scores.api.consts.SrcEnum;
import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.InsertScoresLog;
import com.cj.scores.api.pojo.PagedList;
import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.UserScores;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.api.pojo.select.ScoreLogSelect;
import com.cj.scores.api.pojo.select.ScoreSelect;
import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.cache.LocalCache;
import com.cj.scores.service.cfg.JedisCache;
import com.cj.scores.service.util.RedisLockUtil;
import com.cj.scores.service.util.ResultUtil;
import com.cj.scores.service.util.ValidatorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ScoreService implements ScoresApi {

    @Autowired
    private ScoresMapper scoresMapper;
    @Autowired
    private JedisCache jedisCache;
    @Autowired
    private LocalCache localCache;

    public Result updateUserScoresGrpc(@Valid UserScoresRequest request) {
        if (ValidatorUtil.isEmpty(request.getSrcId(), request.getType(), request.getChangeScores(), request.getUid(),
                request.getOrderNo()) || StringUtils.isEmpty(request.getOrderNo())
                || SrcEnum.getTypeName(request.getSrcId()) == null) {
            return ResultUtil.paramNullResult();
        }
        return updateUserScores(request);
    }

    public Result updateUserScores(UserScoresRequest request) {
        Result result = null;
        String key = JEDIS_PREFIX_LOCK + String.valueOf(request.getUid());
        //redisson lock
        RLock lock = RedisLockUtil.lock(key, TimeUnit.SECONDS, 10);
        try {
            final double changeScore = request.getChangeScores();
            long uid = request.getUid();
            log.info("update score begin uid={}, changeScore={}", uid, changeScore);
            int var1 = 0;
            final int type = request.getType();
            double fromScores = 0.0;
            //local cache key  uid=srcId+orderNo
            String localKey = uid + "=" + request.getSrcId() + request.getOrderNo();
            UserScores userScores = scoresMapper.selectScoresById(uid);
            boolean b = localCache.orderIsExist(localKey);
            if (b)
                return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1107, ResultConsts.ERR_1107_MSG);
            if (userScores == null) {
                //插入操作
                if (INCOME != type)
                    return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                request.setScores(changeScore);
                request.setTotalScores(changeScore);
                request.setEnabled(Boolean.TRUE);
                var1 = scoresMapper.insertUserScores(request);
            } else {
                //更新操作
                switch (type) {
                    case INCOME:
                        //收入
                        request.setTotalScores(userScores.getTotalScores() + changeScore);
                        request.setScores(userScores.getScores() + changeScore);
                        break;
                    case UNLOCK_INCRE:
                        //解锁增
                        if (changeScore > userScores.getLockScores())
                            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                        request.setLockScores(userScores.getLockScores() - changeScore);
                        request.setScores(userScores.getScores() + changeScore);
                        break;
                    case UNLOCK_DECRE:
                        //解锁减
                        if (changeScore > userScores.getLockScores())
                            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                        request.setLockScores(userScores.getLockScores() - changeScore);
                        break;
                    default:
                        //锁定或支出
                        if (changeScore > userScores.getScores())
                            return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.ERR_1106, ResultConsts.SCORES_NOT_FULL_MSG);
                        request.setScores(userScores.getScores() - changeScore);
                        request.setLockScores(LOCK == type ? userScores.getLockScores() + changeScore : null);
                        break;
                }
                //得到起始分数
                fromScores = userScores.getScores();
                request.setVersion(userScores.getVersion());
                var1 = scoresMapper.updateUserScores(request);
            }
            result = ResultUtil.getDmlResult(var1);
            log.info("uid={} update scores result={}", uid, result.getMsg());
            boolean insertSucceed = var1 > 0;
            if (insertSucceed) {
                //如未冲突 则更新
                Double nowScore = request.getScores();
                InsertScoresLog log2 = this.setLogObjByScores(request);
                log2.setScores(nowScore == null ? changeScore : nowScore);
                log2.setFromScores(fromScores);
                int var2 = scoresMapper.insertScoresLog(log2);
                result.setData(log2.getId());
                localCache.putKey(localKey, Boolean.TRUE);
                jedisCache.hdel(JEDIS_PREFIX, String.valueOf(uid));
                log.info("uid = {} insert score log result={}", uid, var2 > 0);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            result = ResultUtil.getServerErrorResult();
            log.error("guava loading cache error msg = {}", e.getMessage());
        } catch (DataAccessException e) {
            e.printStackTrace();
            result = ResultUtil.getServerErrorResult();
            log.error("mybatis error msg = {}", e.getMessage());
        } finally {
            //redisson unlock
            if (lock.isHeldByCurrentThread()) lock.forceUnlock();
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
            } else {
                RLock lock = RedisLockUtil.lock(JEDIS_PREFIX + uid, TimeUnit.SECONDS, 10);
                try {
                    //insert one record
                    UserScoresRequest request = new UserScoresRequest();
                    request.setUid(uid);
                    request.setEnabled(Boolean.TRUE);
                    request.setProperties("{}");
                    int var1 = scoresMapper.insertUserScores(request);
                    if (var1 > 0) {
                        hget = getUserScoreByUid(uid);
                    }
                } finally {
                    if (lock.isHeldByCurrentThread()) lock.forceUnlock();
                }

            }
        }
        return hget;
    }


    public PagedList<UserScoreLogDto> getScoreLogList(ScoreLogSelect select) {
        Page<Object> objects = null;
        List<UserScoreLogDto> returnList = new ArrayList<>();
        Integer pageNum = select.getPage_num();
        Integer pageSize = select.getPage_size();
        if (pageNum != null && pageSize != null) {
            objects = PageHelper.startPage(pageNum, pageSize);
        } else {
            pageNum = 0;
            pageSize = 0;
        }
        select.setTable(ValidatorUtil.getPaylogTableNameByUid(select.getUid()));
        List<UserScoreLogDto> scoreLogDetail = scoresMapper.getScoreLogDetail(select);
        scoreLogDetail.forEach(x -> {
            x.setSrc(SrcEnum.getTypeName(x.getSrcId()));
            x.setTypeName(ScoreTypeEnum.getTypeName(x.getType()));
            returnList.add(x);
        });
        return new PagedList<>(returnList, objects == null ? 0 : objects.getTotal(), pageNum, pageSize);
    }


    private InsertScoresLog setLogObjByScores(UserScoresRequest request) {
        InsertScoresLog log2 = new InsertScoresLog();
        log2.setTableName(ValidatorUtil.getPaylogTableNameByUid(request.getUid()));
        log2.setChangeScores(request.getChangeScores());
        log2.setComment(request.getComment());
        log2.setSrcId(request.getSrcId());
        log2.setType(request.getType());
        log2.setUid(request.getUid());
        log2.setOrderNo(request.getOrderNo());
        log2.setSrc(SrcEnum.getTypeName(request.getSrcId()));
        log2.setId(UUID.randomUUID().toString());
        return log2;
    }
}
