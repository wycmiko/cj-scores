package com.cj.shop.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuchuanWeng( )
 * @date 2018/7/6
 * @since 1.0
 */
@Getter
@Setter
public class CartDeleteRequest implements Serializable {
    String token;

    @JsonProperty("cart_id_list")
    List<Long> cartList;
}
