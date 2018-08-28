package com.cj.scores.api.pojo.select;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/28
 * @since 1.0
 */
@Getter
@Setter
public class ScoreSelect implements Serializable {
    private Integer page_num;
    private Integer page_size;
    private Long uid;
    private Integer sort_flag;
}
