package com.cj.scores.api.pojo.select;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @date 2018/8/28
 * @since 1.0
 */
@Getter
@Setter
public class ScoreSelect implements Serializable {
    private Integer page_num;
    private Integer page_size;
    private Long uid;
    /**
     * 1=当前积分降序
     * 2=当前积分升序
     * null = 默认创建时间倒序
     */
    private Integer sort_flag;

    private String start_time;

    private String end_time;
}
