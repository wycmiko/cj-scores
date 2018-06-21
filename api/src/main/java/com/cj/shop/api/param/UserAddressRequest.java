package com.cj.shop.api.param;

import com.cj.shop.common.model.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/6/21
 * @since 1.0
 */
@Getter
@Setter
public class UserAddressRequest extends PropertyEntity implements Serializable {
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
}
