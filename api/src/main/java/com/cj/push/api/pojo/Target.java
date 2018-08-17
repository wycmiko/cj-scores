package com.cj.push.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 推送事件 目标群体Bean
 * @author yuchuanWeng
 * @date 2018/8/15
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Target implements Serializable {
    /**
     * 1=tag标签数组(or关系)
     * 2=alias 别名数组 一次推送最多 1000 个
     * 3=all全部用户
     * 4=tag_and
     * 5=tag_not
     */
    @NotNull
    @JsonProperty("push_type")
    private Integer pushType;
    /**
     * 推送目标列表
     */
    private List<String> array = new ArrayList<>();
}
