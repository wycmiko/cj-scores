package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yuchuanWeng
 * @date 2018/7/20
 * @since 1.0
 */
@Getter
@Setter
public class FileDeleteRequest {
    @JsonProperty("file_url")
    private String fileUrl;
    private String token;
}
