package com.cj.scores.api.consts;

import lombok.Getter;

/**
 * 积分收支类型
 * @author yuchuanWeng
 * @date 2018/8/28
 * @since 1.0
 */
@Getter
public enum ScoreTypeEnum {
    INCOME(1, "收入"),
    OUTCOMT(2, "支出"),
    LOCK(3, "冻结"),
    UNLOCK(4, "解冻"),
    UNKNOWN(5, "未知类型");

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
                return OUTCOMT.typeName;
            case 3:
                return LOCK.typeName;
            case 4:
                return UNLOCK.typeName;
        }
        return UNKNOWN.typeName;
    }
}
