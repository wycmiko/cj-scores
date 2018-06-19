package com.cj.shop.api.entity;

import com.cj.shop.common.model.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * mybatis自动生成实体类
 * 对应个人表 cj_match.m_personals
 *
 * @author yuchuanWeng
 * @version 1.0
 * @date 2018-03-22
 */
@Getter
@Setter
public class Personals extends PropertyEntity implements Serializable {
    /**
     * 参与者id
     */
    private Integer playerId;

    private String openId;
    private String unionId;
    /**
     * uid
     */
    private Long uid;
    /**
     * 国籍ID
     */
    private Integer countryId;

    /**
     * 国籍ID
     */
    private String countryName;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 真实姓名
     */
    private String personalName;
    /**
     * 证件类型
     * 1=身份证
     * 2=other
     */
    private Integer cardType;
    /**
     * 证件号
     */
    private String cardNo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 启用/禁用 true/false
     */
    private String enabled;

    private Integer state;

    private String stateDesc;

    private String createTime;

}