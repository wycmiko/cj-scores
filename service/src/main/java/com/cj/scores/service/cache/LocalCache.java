package com.cj.scores.service.cache;

import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.select.ScoreLogSelect;
import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.util.ValidatorUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 积分日志本地缓存
 *
 * @author yuchuanWeng
 * @date 2018/8/31
 * @since 1.0
 */
@Slf4j
@Component
public class LocalCache {

    @Autowired
    private ScoresMapper scoresMapper;


    private LoadingCache<String, Boolean> cache = CacheBuilder.newBuilder().maximumSize(2000)
            .build(new CacheLoader<String, Boolean>() {
                @Override
                public Boolean load(String s) throws Exception {
                    log.info("load value by db");
                    return getObjByKey(s);
                }
                private Boolean getObjByKey(String key) {
                    ScoreLogSelect select = new ScoreLogSelect();
                    long uid = Long.valueOf(key.split("=")[0]);
                    String key2 = key.split("=")[1];
                    select.setOrder_no(key2);
                    select.setUid(uid);
                    select.setTable(ValidatorUtil.getPaylogTableNameByUid(uid));
                    List<UserScoreLogDto> scoreLogDetail = scoresMapper.getScoreLogDetail(select);
                    return (scoreLogDetail != null && !scoreLogDetail.isEmpty()) ? true : false;
                }
            });


    /**
     * 判断订单号是否已经存在
     *
     * @param key
     * @return true = 已存在 false=不存在
     */
    public boolean orderIsExist(String key) throws ExecutionException {
        if (StringUtils.isEmpty(key)) return false;
        return cache.get(key);
    }

    public void delKey(String key) {
        cache.invalidate(key);
    }


    public void putKey(String key, Boolean value) {
        cache.put(key, value);
    }

}

