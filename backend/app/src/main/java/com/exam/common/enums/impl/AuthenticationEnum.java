package com.exam.common.enums.impl;


import com.exam.common.enums.EnumCode;

public enum AuthenticationEnum implements EnumCode {

    Have_No_Right(40003, "不好意思，您没有权限"),
    Login_Successfully(20000, "登录成功"),
    Login_Failure(40002, "账号或密码错误，请输入正确的的信息"),
    Not_Login (40001, "您未登录，请先登录"),
    ;


    private final long code;
    private final String msg;

    AuthenticationEnum(long code, String msg) {
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
