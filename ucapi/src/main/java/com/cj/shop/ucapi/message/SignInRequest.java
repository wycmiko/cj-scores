package com.cj.shop.ucapi.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年03月23日</p>
 * <p>@author tangxd</p>
 **/
@Data
public class SignInRequest implements Serializable {
    @JsonProperty("user_name")
    private String user_name;
    @JsonProperty("password")
    private String password;
    /**
     * 1=pc,2=app,3=other
     */
    private short platform;
}
