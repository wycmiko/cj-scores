package com.cj.scores.api.consts;

import lombok.Getter;

/**
 * 积分收支类型
 *
 * @date 2018/8/28
 * @since 1.0
 */
@Getter
public enum ScoreTypeEnum {
    INCOME(1, "收入"),
    OUTCOME(2, "支出"),
    LOCK(3, "冻结"),
    UNLOCK_INCRE(4, "解锁增"),
    UNLOCK_DECRE(5, "解锁减");

    private int type;
    private String typeName;

    ScoreTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }


    public static String getTypeName(int type) {
        switch (type) {
            case 1:
                return INCOME.typeName;
            case 2:
                return OUTCOME.typeName;
            case 3:
                return LOCK.typeName;
            case 4:
                return UNLOCK_INCRE.typeName;
            case 5:
                return UNLOCK_DECRE.typeName;
            default:
                return "未知类型";
        }
    }

    public static int getType(int type) {
        switch (type) {
            case 1:
                return INCOME.type;
            case 2:
                return OUTCOME.type;
            case 3:
                return LOCK.type;
            case 4:
                return UNLOCK_INCRE.type;
            case 5:
                return UNLOCK_DECRE.type;
        }
        return -1;
    }
}
