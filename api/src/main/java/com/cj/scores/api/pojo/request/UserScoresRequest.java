package com.cj.scores.api.pojo.request;

import com.cj.scores.api.pojo.UserScores;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Getter
@Setter
public class UserScoresRequest extends UserScores implements Serializable {
    @NotNull
    @JsonProperty("change_scores")
    private Double changeScores;
    @NotNull
    @JsonProperty("src_id")
    private Integer srcId;
    @NotBlank
    private String src;
    private String comment;
}
