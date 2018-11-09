package com.cj.scores.dao.mapper;

import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.InsertScoresLog;
import com.cj.scores.api.pojo.UserScores;
import com.cj.scores.api.pojo.select.ScoreLogSelect;
import com.cj.scores.api.pojo.select.ScoreSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Mapper
public interface ScoresMapper {

    /**
     * 判断表是否存在
     *
     * @param tableName
     * @return
     */
    String isExistTable(@Param("tableName") String tableName);

    /**
     * 创建积分日志表 t_user_scores_log_0x
     *
     * @param tableName
     * @return
     */
    int createScoresLogTable(@Param("tableName") String tableName);

    int insertUserScores(UserScores userScores);

    int updateUserScores(UserScores userScores);

    int insertScoresLog(InsertScoresLog log);

    UserScores selectScoresById(Long uid);

    List<Long> selectAllScores(ScoreSelect select);

    List<UserScoreLogDto> getScoreLogDetail(ScoreLogSelect select);

    UserScoreLogDto getScoreLogDetailByid(@Param("table") String table, @Param("id") String id, @Param("uid") Long uid);

    /**
     * 2018-11-6 new
     * 统计 获取总数量/消耗总数量/当前总数量
     */
    double getLogScoreCount(@Param("table") String tableName, @Param("type") int type);

    /**
     * 当前积分总量
     * @return
     */
    double getScoreCountNow();

    /**
     * 当前总用户数
     */
    long getUserCount();


}
