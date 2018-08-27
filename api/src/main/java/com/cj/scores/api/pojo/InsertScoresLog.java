package com.cj.scores.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 积分记录 插入对象
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Getter
@Setter
public class InsertScoresLog extends UserScoreLog implements Serializable {
    /**
     * 要插入的分表表名
     */
    private String tableName;
}
