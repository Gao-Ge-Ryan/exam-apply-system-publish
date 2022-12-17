package com.exam.common.enums.impl;

import com.exam.common.enums.EnumCode;

public enum UserRoleEnum implements EnumCode {

    User(1,"普通用户"),
    Admin (2,"管理员"),
    Student(3,"学生"),
    Teacher(4,"老师"),
    ;

    private final long code;
    private final String msg;

    UserRoleEnum(long code, String msg) {
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
