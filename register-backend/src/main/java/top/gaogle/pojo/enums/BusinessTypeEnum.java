package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

/**
 * 业务操作类型
 *
 * @author gaogle
 */

public enum BusinessTypeEnum implements IndexedEnum<Integer> {
    /**
     * 其它
     */
    OTHER(0, "其它"),

    /**
     * 新增
     */
    INSERT(1, "新增"),

    /**
     * 修改
     */
    UPDATE(2, "修改"),

    /**
     * 删除
     */
    DELETE(3, "删除"),

    /**
     * 授权
     */
    GRANT(4, "授权"),

    /**
     * 清空数据
     */
    CLEAN(5, "清空数据");


    private final Integer value;
    private final String title;

    BusinessTypeEnum(Integer value, String title) {
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
