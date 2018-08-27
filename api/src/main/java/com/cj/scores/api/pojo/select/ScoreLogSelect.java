package com.cj.scores.api.pojo.select;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 积分日志搜索对象
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Getter
@Setter
public class ScoreLogSelect implements Serializable {
    private String id;
    private String uid;
    private int type;
    @JsonProperty("src_id")
    private int srcId;

    private String table;
}
