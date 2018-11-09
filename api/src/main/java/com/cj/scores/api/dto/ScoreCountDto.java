package com.cj.scores.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 积分统计bean
 * @author yuchuanWeng
 * @date 2018/11/7
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class ScoreCountDto implements Serializable {

    /**
     * 总收入积分
     */
    @JsonProperty("total_income")
    private double totalIncome;

    /**
     * 总消耗积分
     */
    @JsonProperty("total_outcome")
    private double totalOutcome;

    /**
     * 当前总积分
     */
    @JsonProperty("total_scores")
    private double totalScores;


    @JsonProperty("avg_scores")
    private String avgScores;
}
