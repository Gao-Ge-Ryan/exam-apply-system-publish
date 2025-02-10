package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.IndexedEnum;

public enum RoleTypeEnum implements IndexedEnum<Integer> {


    SYSTEM_ROLE(1, "系统角色"),

    ENTERPRISE_ROLE(2, "企业角色"),
    ;

    private final Integer value;
    private final String title;

    RoleTypeEnum(Integer value, String title) {
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
