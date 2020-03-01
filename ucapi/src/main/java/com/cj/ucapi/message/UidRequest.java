package com.cj.ucapi.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年03月23日</p>
 * <p>@author  </p>
 **/
@Getter
@Setter
public class UidRequest implements Serializable {
    private long uid;
}
