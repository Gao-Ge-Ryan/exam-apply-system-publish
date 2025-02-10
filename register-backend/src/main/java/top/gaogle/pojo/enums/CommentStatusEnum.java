package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum CommentStatusEnum implements IndexedEnum<Integer> {

    /**
     * 新建
     */
    NEW(0, "新建"),

    /**
     * 进行中
     */
    ON(1, "进行中"),

    /**
     * 关闭
     */
    OFF(2, "关闭");

    private final Integer value;
    private final String title;

    CommentStatusEnum(Integer value, String title) {
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
