package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum EnterpriseStatusEnum implements IndexedEnum<Integer> {

    /**
     * 新申请
     */
    NEW(0, "新申请"),

    /**
     * 待审核
     */
    PENDING_REVIEW(1, "待审核"),

    /**
     * 审核不通过
     */
    REVIEW_FAILED(2, "审核不通过"),

    /**
     * 审核通过
     */
    REVIEW_PASSED(3, "审核通过");

    private final Integer value;
    private final String title;

    EnterpriseStatusEnum(Integer value, String title) {
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
