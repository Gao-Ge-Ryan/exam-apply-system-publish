package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum FormTemplateRangeEnum implements IndexedEnum<Integer> {

    /**
     * 新申请
     */
    ALL_VIEW(0, "全部可见"),

    /**
     * 待审核
     */
    ENTERPRISE_VIEW(1, "企业可见"),

    ;

    private final Integer value;
    private final String title;

    FormTemplateRangeEnum(Integer value, String title) {
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
