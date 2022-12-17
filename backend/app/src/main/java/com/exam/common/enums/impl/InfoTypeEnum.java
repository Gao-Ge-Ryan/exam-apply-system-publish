package com.exam.common.enums.impl;

import com.exam.common.enums.EnumCode;

public enum InfoTypeEnum implements EnumCode {

    Registration_Time_Announcement(1,"报名时间公告"),
    Notice_of_Examination_Time (2,"打印准考证公告"),
    Examination_Announcement  (3,"成绩查询公告"),
    ;

    private final long code;
    private final String msg;

    InfoTypeEnum(long code, String msg) {
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
