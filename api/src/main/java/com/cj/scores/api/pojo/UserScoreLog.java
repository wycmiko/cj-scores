package com.cj.scores.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Getter
@Setter
public class UserScoreLog implements Serializable {
    private String id;
    private Long uid;
    @JsonProperty("from_scores")
    private Double fromScores;
    private Double scores;
    @JsonProperty("change_scores")
    private Double changeScores;
    /**
     * 1=增加 2=减少 3=冻结
     */
    private Integer type;
    private String src;
    @JsonProperty("src_id")
    private Integer srcId;
    private String comment;
    @JsonProperty("create_time")
    private String createTime;
}
