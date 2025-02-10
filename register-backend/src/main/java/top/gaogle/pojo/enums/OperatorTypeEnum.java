package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

/**
 * 操作人类别
 *
 * @author gaogle
 */
public enum OperatorTypeEnum implements IndexedEnum<Integer> {

    /**
     * 其它
     */
    OTHER(0, "其它"),

    /**
     * 客户端
     */
    CLIENT(1, "客户端"),

    /**
     * 企业端
     */
    ENTERPRISE(2, "企业端"),

    /**
     * 管理端
     */
    ADMIN(3, "管理端");

    private final Integer value;
    private final String title;

    OperatorTypeEnum(Integer value, String title) {
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
