package com.cj.scores.api.consts;

import lombok.Getter;

/**
 * 来源类型
 * @date 2018/8/28
 * @since 1.0
 */
@Getter
public enum SrcEnum {
    APP(1, "珑讯APP"),
    H5(2, "珑讯H5"),
    TASKS(3, "任务平台"),
    MANAGE(4, "珑讯后台管理");


    private int src;
    private String name;

    SrcEnum(int src, String name) {
        this.src = src;
        this.name = name;
    }


    public static String getTypeName(int type) {
        switch (type) {
            case 1:
                return APP.name;
            case 2:
                return H5.name;
            case 3:
                return TASKS.name;
            case 4:
                return MANAGE.name;
        }
        return null;
    }
}
