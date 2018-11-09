package com.cj.scores.service.impl;

import com.cj.scores.api.ScoresApi;
import com.cj.scores.api.dto.ScoreCountDto;
import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.cfg.JedisCache;
import com.cj.scores.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 积分统计 服务层
 *
 * @author yuchuanWeng
 * @date 2018/11/7
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CountService {
    @Autowired
    private ScoresMapper scoresMapper;
    @Autowired
    private JedisCache jedisCache;


    /**
     * 1h 更新一次
     *
     * @return
     */
    public ScoreCountDto getScoreCount() {
        ScoreCountDto dto = jedisCache.get(ScoresApi.JEDIS_PREFIX_COUNT, ScoreCountDto.class);
        if (dto == null) {
            dto = new ScoreCountDto();
            //总收入 总支出
            dto.setTotalIncome(getStatisticScores(ScoresApi.INCOME));
            dto.setTotalOutcome(getStatisticScores(ScoresApi.OUTCOME));
            double scoreCountNow = scoresMapper.getScoreCountNow();
            double v = scoresMapper.getUserCount() * 1.0;
            dto.setTotalScores(scoreCountNow);
            dto.setAvgScores(String.format("%.2f", scoreCountNow / v));
            jedisCache.setByDefaultTime(ScoresApi.JEDIS_PREFIX_COUNT, dto);
        }
        return dto;
    }

    /**
     * 手动刷新数据
     *
     * @return
     */
    public ScoreCountDto refreshData() {
        jedisCache.del(ScoresApi.JEDIS_PREFIX_COUNT);
        return getScoreCount();
    }


    private double getStatisticScores(int type) {
        double totalScores = 0.0;
        for (int i = 0; i < 8; i++) {
            String paylogTableName = ValidatorUtil.getPaylogTableName(i);
            totalScores += scoresMapper.getLogScoreCount(paylogTableName, type);
        }
        return totalScores;
    }


}
