package com.exam.common.enums.impl;

import com.exam.common.enums.EnumCode;

public enum ExamStatusEnum implements EnumCode {

    NotStarted(0,"未开始"),
    Start(1,"报名中"),
    Stop(2,"打印准考证"),
    Score_Inquiry(3,"成绩查询");

    private final long code;
    private final String msg;

    ExamStatusEnum(long code, String msg) {
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
