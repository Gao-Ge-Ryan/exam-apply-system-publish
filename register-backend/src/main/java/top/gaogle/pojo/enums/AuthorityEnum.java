package top.gaogle.pojo.enums;

import top.gaogle.framework.pojo.AuthorityEnumModel;
import top.gaogle.framework.pojo.IndexedEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限枚举
 * <p>
 * shift取值范围：1~62，最高位符号位
 * MODULE所有权限二进制值：0111111111111111111111111111111111111111111111111111111111111110
 *
 * @author goge
 * @since 1.0.0
 */
public enum AuthorityEnum implements IndexedEnum<String> {

    /**
     * 用户管理
     */
    USER("user", "user", "用户管理", 0L, Type.MODULE),
    USER_VIEW(USER, "user_view", "用户查看", 1L, Type.UNIT),
    USER_PUT(USER, "user_put", "用户修改", 2L, Type.UNIT),
    USER_DELETE(USER, "user_delete", "用户删除", 3L, Type.UNIT),
    USER_PUT_ROLE(USER, "user_put_role", "设置角色", 4L, Type.UNIT),

    /**
     * 角色管理
     */
    ROLE("role", "role", "角色管理", 0L, Type.MODULE),
    ROLE_VIEW(ROLE, "role_view", "角色查看", 1L, Type.UNIT),
    ROLE_PUT(ROLE, "role_put", "角色修改", 2L, Type.UNIT),
    ROLE_DELETE(ROLE, "role_delete", "角色删除", 3L, Type.UNIT),
    ROLE_INSERT(ROLE, "role_insert", "添加角色", 4L, Type.UNIT),
    ROLE_PUT_AUTHORITY(ROLE, "role_put_authority", "设置权限", 5L, Type.UNIT),

    /**
     * 权限管理
     */
    AUTHORITY("authority", "authority", "权限管理", 0L, Type.MODULE),
    AUTHORITY_VIEW(AUTHORITY, "authority_view", "权限查看", 1L, Type.UNIT),
    /**
     * 操作日志
     */
    OPERATION_LOG("operation_log", "operation_log", "操作日志", 0L, Type.MODULE),
    OPERATION_LOG_VIEW(OPERATION_LOG, "operation_log_view", "查看操作日志", 1L, Type.UNIT),

    /**
     * 咨询反馈
     */
    FEEDBACK("feedback", "feedback", "咨询反馈", 0L, Type.MODULE),
    FEEDBACK_VIEW(FEEDBACK, "feedback_view", "咨询反馈查看", 1L, Type.UNIT),

    /**
     * 企业管理
     */
    ENTERPRISE("enterprise", "enterprise", "企业管理", 0L, Type.MODULE),
    ENTERPRISE_VIEW(ENTERPRISE, "enterprise_view", "企业查看", 1L, Type.UNIT),
    ENTERPRISE_PUT(ENTERPRISE, "enterprise_put", "企业修改", 2L, Type.UNIT),
    ENTERPRISE_DELETE(ENTERPRISE, "enterprise_delete", "企业删除", 3L, Type.UNIT),
    ENTERPRISE_INSERT(ENTERPRISE, "enterprise_insert", "企业添加", 4L, Type.UNIT),

    /**
     * 表单模板管理
     */
    FORM_TEMPLATE("form_template", "form_template", "表单模板管理", 0L, Type.MODULE),
    FORM_TEMPLATE_VIEW(FORM_TEMPLATE, "form_template_view", "表单模板查看", 1L, Type.UNIT),

    /**
     * 报名发布管理
     */
    REGISTER_PUBLISH("register_publish", "register_publish", "报名发布管理", 0L, Type.MODULE),
    REGISTER_PUBLISH_VIEW(REGISTER_PUBLISH, "register_publish_view", "报名发布查看", 1L, Type.UNIT),
    ;

    private final String module;
    private final AuthorityEnum parent;
    private final String value;
    private final String title;
    private final Long shift;
    private final Type type;
    private final List<AuthorityEnum> children;


    AuthorityEnum(String module, String value, String title, Long shift, Type type) {
        this(module, null, value, title, shift, type);
    }

    AuthorityEnum(AuthorityEnum parent, String value, String title, Long shift, Type type) {
        this(parent.module, parent, value, title, shift, type);
    }

    /**
     * 注：shift 用于逻辑位移的操作数
     */
    AuthorityEnum(String module, AuthorityEnum parent, String value, String title, Long shift, Type type) {
        this.module = module;
        this.parent = parent;
        this.value = value;
        this.title = title;
        if (shift == null) {
            this.shift = 0L;
        } else {
            this.shift = 1L << shift; // 逻辑位移
        }
        this.type = type;
        this.children = new ArrayList<>();
        if (this.parent != null) {
            this.parent.children.add(this);
        }
    }

    public enum Type {
        MODULE, // 功能模块
        UNIT, // 功能单元
    }


    @Override
    public String value() {
        return value;
    }

    @Override
    public String title() {
        return title;
    }

    public Type type() {
        return type;
    }

    public List<AuthorityEnum> children() {
        return children;
    }

    public String module() {
        return module;
    }

    public Long shift() {
        return shift;
    }

    public AuthorityEnum parent() {
        return parent;
    }


    // 访问父类枚举
    public static List<AuthorityEnum> getAllParentEnum() {
        return Arrays.stream(AuthorityEnum.values())
                .filter(value -> Type.MODULE.equals(value.type))
                .collect(Collectors.toList());
    }

    // 访问父类枚举
    public static List<AuthorityEnumModel> getAllParentEnumModel() {
        return getAllParentEnum().stream()
                .map(AuthorityEnumModel::new)
                .collect(Collectors.toList());
    }

}
