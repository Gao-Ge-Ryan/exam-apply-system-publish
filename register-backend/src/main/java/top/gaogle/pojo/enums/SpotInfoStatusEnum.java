package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum SpotInfoStatusEnum implements IndexedEnum<Integer> {

    NOT_ENABLED(0, "未启用"),
    ENABLE(1, "启用"),

    ;

    private final Integer value;
    private final String title;

    SpotInfoStatusEnum(Integer value, String title) {
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
