package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

/**
 * 操作状态
 *
 * @author gaogle
 */
public enum BusinessStatusEnum implements IndexedEnum<Integer> {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 失败
     */
    FAIL(500, "失败"),
    ;

    private final Integer value;
    private final String title;

    BusinessStatusEnum(Integer value, String title) {
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
