package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum FormTemplateFlagEnum implements IndexedEnum<Integer> {

    FIRST(1, "模板一"),

    SECOND(2, "模板二"),


    THIRD (3, "模板三");

    private final Integer value;
    private final String title;

    FormTemplateFlagEnum(Integer value, String title) {
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
