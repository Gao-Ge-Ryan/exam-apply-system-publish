package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum DynamicRegisterInfoApproveEnum implements IndexedEnum<Integer> {

    /**
     * 待审核
     */
    PENDING_REVIEW(0, "待审核"),

    /**
     * 审核通过
     */
    APPROVED(1, "审核通过"),

    /**
     * 审核失败
     */
    REJECTED(2, "审核失败");

    private final Integer value;
    private final String title;

    DynamicRegisterInfoApproveEnum(Integer value, String title) {
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

    /**
     * 根据 value 值查找对应的枚举值
     *
     * @param value 枚举的数值
     * @return 对应的枚举常量
     * @throws IllegalArgumentException 如果没有对应的枚举值，抛出异常
     */
    public static DynamicRegisterInfoApproveEnum fromValue(Integer value) {
        for (DynamicRegisterInfoApproveEnum enumValue : DynamicRegisterInfoApproveEnum.values()) {
            if (enumValue.value().equals(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

}
