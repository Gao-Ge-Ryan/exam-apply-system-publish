package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

/**
 * 阿里支付交易状态
 */
public enum AlipayTradeStatusEnum implements IndexedEnum<String> {

    /**
     * 交易成功
     */
    TRADE_SUCCESS("TRADE_SUCCESS", "交易成功"),

    /**
     * 交易完成（不可退款状态）
     */
    TRADE_FINISHED("TRADE_FINISHED", "交易完成"),
    ;


    private final String value;
    private final String title;

    AlipayTradeStatusEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }


    @Override
    public String value() {
        return value;
    }

    @Override
    public String title() {
        return title;
    }
}
