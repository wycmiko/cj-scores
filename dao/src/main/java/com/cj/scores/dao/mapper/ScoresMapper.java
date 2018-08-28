package com.cj.scores.dao.mapper;

import com.cj.scores.api.dto.UserScoreLogDto;
import com.cj.scores.api.pojo.InsertScoresLog;
import com.cj.scores.api.pojo.UserScores;
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

    List<UserScoreLogDto> getScoreLogDetail(@Param("table") String table, @Param("uid") Long uid);


}
