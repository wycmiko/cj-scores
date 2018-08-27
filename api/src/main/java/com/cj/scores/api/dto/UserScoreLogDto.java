package com.cj.scores.api.dto;

import com.cj.scores.api.pojo.UserScoreLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class UserScoreLogDto extends UserScoreLog implements Serializable {
    /**
     * 类型描述
     */
    private String typeName;
}
