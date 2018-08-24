package com.cj.ucapi.message;

/**
 * <p>Create Time: 2018年03月22日</p>
 * <p>@author tangxd</p>
 **/
public enum Status {
    SUCCESS(0),
    ERROR(1),
    CONNECT_FAIL(2),
    UNRECOGNIZED(-1);

    private int value = 0;

    Status(int value) {
        this.value = value;
    }

    public static Status getEnum(int value) {
        switch (value) {
            case 0:
                return SUCCESS;
            case 1:
                return ERROR;
            case 2:
                return CONNECT_FAIL;
            case -1:
                return UNRECOGNIZED;
            default:
                throw new IllegalArgumentException("参数非法");
        }
    }
}