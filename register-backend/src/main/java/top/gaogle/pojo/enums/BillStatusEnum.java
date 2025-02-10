package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum BillStatusEnum implements IndexedEnum<Integer> {
    /**
     * 初始化
     */
    INIT(0, "初始化"),

    /**
     * 有效 (成功)
     */
    VALID(1, "有效"),

    /**
     * 无效（可以删掉的：超过订单支付时间,定时任务处理的订单，只有定时任务生成此状态订单）
     */
    INVALID(2, "无效"),

    /**
     * 人工处理（收到支付宝通知但不是 {@link top.gaogle.pojo.enums.AlipayTradeStatusEnum#TRADE_FINISHED}
     * 或 {@link top.gaogle.pojo.enums.AlipayTradeStatusEnum#TRADE_SUCCESS}）
     */
    MANUAL_PROCESSING(3, "人工处理"),
    ;

    private final Integer value;
    private final String title;

    BillStatusEnum(Integer value, String title) {
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
