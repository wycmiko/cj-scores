package com.cj.scores.api;

import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.PagedList;
import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.UserScores;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.api.pojo.select.ScoreLogSelect;
import com.cj.scores.api.pojo.select.ScoreSelect;

/**
 * 积分处理api
 * api-url: <a href='http://172.28.3.45:3008/project/39/interface/api'>http://172.28.3.45:3008/project/39/interface/api</a>
 *
 * @author yuchaunWeng
 */
public interface ScoresApi {

    String JEDIS_PREFIX = "cj_scores:user:";
    String JEDIS_PREFIX_LOCK = JEDIS_PREFIX + "lock:";
    /**
     * score type：
     *
     * 1=收入
     * 2=支出
     * 3=冻结
     * 4=解冻增
     * 5=解冻减
     */
    int INCOME = 1;
    int OUTCOME = 2;
    int LOCK = 3;
    /**
     * 解锁增
     */
    int UNLOCK_INCRE = 4;
    /**
     * 解锁减
     */
    int UNLOCK_DECRE = 5;

    /**
     * 变更用户积分
     *
     * @param request
     * @return
     */
    Result updateUserScores(UserScoresRequest request);

    /**
     * 查询用户积分列表
     *
     * @param select
     * @return
     */
    PagedList<UserScores> getUserScoreList(ScoreSelect select);

    /**
     * 查询用户积分
     *
     * @param uid
     * @return
     */
    UserScores getUserScoreByUid(Long uid);

    /**
     * 查询用户积分收支详情
     *
     * @param select
     * @return
     */
    PagedList<UserScoreLogDto> getScoreLogList(ScoreLogSelect select);


}
