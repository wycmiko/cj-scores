package com.cj.scores.api.pojo.select;

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
    private String order_no;
    private String token;
    private Long uid;
    private int type;
    private int src_id;
    private Integer page_num;
    private Integer page_size;
    private String table;
}
