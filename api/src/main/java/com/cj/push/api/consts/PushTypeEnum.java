package com.cj.push.api.consts;

/**
 * 推送目标类型枚举
 */
public enum PushTypeEnum {

    TAG(1, "标签用户"),
    ALIAS(2, "别名用户"),
    ALL(3, "全部用户");

    private int type;
    private String desc;

    PushTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static PushTypeEnum getEnum(int value) {
        switch (value) {
            case 1:
                return TAG;
            case 2:
                return ALIAS;
            case 3:
                return ALL;
            default:
                throw new IllegalArgumentException("参数非法");
        }
    }

    public static int getCode(int value) {
        switch (value) {
            case 1:
                return TAG.type;
            case 2:
                return ALIAS.type;
            case 3:
                return ALL.type;
            default:
                throw new IllegalArgumentException("参数非法");
        }
    }


    public static String getDesc(int value) {
        switch (value) {
            case 1:
                return TAG.desc;
            case 2:
                return ALIAS.desc;
            case 3:
                return ALL.desc;
            default:
                throw new IllegalArgumentException("参数非法");
        }
    }
}
