package com.exam.common.enums.impl;


import com.exam.common.enums.EnumCode;

/**
 * 原始枚举（封装result用）
 *
 * @author gaoge
 * @since 2021-11-07 13:42:39
 */
public enum ApiEnumCode implements EnumCode {
    FAILED(40000L, "失败"),
    SUCCESS(20000L, "成功");


    private final long code;
    private final String msg;

    ApiEnumCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiEnumCode fromCode(long code) {
        ApiEnumCode[] ecs = values();
        ApiEnumCode[] var3 = ecs;
        int var4 = ecs.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ApiEnumCode ec = var3[var5];
            if (ec.getCode() == code) {
                return ec;
            }
        }

        return SUCCESS;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ApiEnumCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
