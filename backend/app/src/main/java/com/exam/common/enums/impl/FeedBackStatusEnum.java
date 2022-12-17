package com.exam.common.enums.impl;

import com.exam.common.enums.EnumCode;

public enum FeedBackStatusEnum implements EnumCode {

    Response(1,"已回复"),
    No_Response(0,"未回复"),

    ;

    private final long code;
    private final String msg;

    FeedBackStatusEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
