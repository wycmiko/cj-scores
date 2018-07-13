package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuchuanWeng( )
 * @date 2018/6/21
 * @since 1.0
 */
@Getter
@Setter
public class UserAddressRequest  implements Serializable {
    private Long id;

    private String token;
    @JsonProperty("user_name")
    private String userName;

    private String mobile;

    private String tel;

    private String province;

    private String city;

    private String area;

    @JsonProperty("post_code")
    private String postCode;

    @JsonProperty("detail_addr")
    private String detailAddr;

    @JsonProperty("default_flag")
    private Integer defaultFlag;

    @JsonProperty("sort_flag")
    private Integer sortFlag;

    private Map<String,Object> properties;
}
