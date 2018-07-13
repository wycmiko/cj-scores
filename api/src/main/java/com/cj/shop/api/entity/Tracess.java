package com.cj.shop.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yuchuanWeng( )
 * @date 2018/7/13
 * @since 1.0
 */
@Getter
@Setter
public class Tracess implements Serializable {
    //物流动态
    private String AcceptStation;
    //时间
    private String AcceptTime;
}
