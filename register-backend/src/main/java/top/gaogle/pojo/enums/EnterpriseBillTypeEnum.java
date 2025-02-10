package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum EnterpriseBillTypeEnum implements IndexedEnum<Integer> {
    /**
     * 充值
     */
    TOP_UP(1, "充值"),

    /**
     * 支出
     */
    EXPENDITURE(2, "支出"),

    ;

    private final Integer value;
    private final String title;

    EnterpriseBillTypeEnum(Integer value, String title) {
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
