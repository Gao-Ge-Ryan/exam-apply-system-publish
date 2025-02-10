package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum RegisterPublishStatusEnum implements IndexedEnum<Integer> {

    NEW(1, "新建"),

    REGISTRATION_ONGOING(2, "报名中"),

    PRINT_EXAM_ADMISSION_TICKET(3, "打印准考证"),

    SCORE_INQUIRY(4, "成绩查询"),

    ONGOING(5, "进行中"),

    COMPLETED(6, "已结束"),

    ;

    private final Integer value;
    private final String title;

    RegisterPublishStatusEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }


    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String title() {
        return title;
    }

}
