package top.gaogle.common;

import java.util.Arrays;
import java.util.List;

/**
 * 常量类
 *
 * @author gaogle
 * @since 1.0.0
 */
public class RegisterConst {

    private RegisterConst() {
        throw new IllegalStateException(RegisterConst.PROHIBIT_INSTANTIATION);
    }

    // go ge
    public static final String GO_GE = "Go-Ge";
    // 禁止实例化
    public static final String PROHIBIT_INSTANTIATION = "Prohibit Instantiation";
    // 密钥
    public static final String AUTHENTICATION_SECRET = "AUTHENTICATION_SECRET";
    // 颁发时间
    public static final String IAT = "iat";
    // 数字10
    public static final Integer TEN = 10;
    // json请求格式
    public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    // mybatis的mapper包路径
    public static final String MYBATIS_MAPPER_SCAN = "top.gaogle.dao";
    // 枚举包
    public static final String ENUM_PACKAGE_PREFIX = "top.gaogle.pojo.enums.";
    // 枚举values方法
    public static final String ENUM_VALUES = "values";
    // 邮箱格式正则表达式
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    // 正则表达式要求密码必须包含大写字母、小写字母、数字和特殊字符(@#$%^&*!)，并且长度必须在8到28个字符之间
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,28}$";
    // http请求
    public static final String HTTP = "http://";
    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 注册信息表名
     */
    public static final String REGISTER_INFO_TABLE_NAME = "register_info_";

    /**
     * 斜杠
     */
    public static final String SLASH = "/";

    /**
     * 点
     */
    public static final String DOT = ".";

    /**
     * 动态字段
     */
    public static final List<String> DYNAMIC_FIELD = Arrays.asList("name","phone_number","score", "status", "reason","approve", "create_by", "create_at", "update_by", "update_at");

    /**
     * key
     */
    public static final String KEY = "key";

    /**
     * value
     */
    public static final String VALUE = "value";

    /**
     * matchType 匹配类型 exact精准
     */
    public static final String MATCH_TYPE = "matchType";

    /**
     * exact 精准
     */
    public static final String MATCH_TYPE_EXACT = "exact";

    /**
     * like 模糊
     */
    public static final String MATCH_TYPE_LIKE = "like";

    /**
     * id
     */
    public static final String ID = "id";

    /**
     * column id
     */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    /**
     * column 审批
     */
    public static final String COLUMN_APPROVE = "approve";

    /**
     * column 审批
     */
    public static final String COLUMN_SCORE = "score";

    /**
     * column 状态
     */
    public static final String COLUMN_STATUS = "status";

    /**
     * column create_by
     */
    public static final String COLUMN_CREATE_BY = "create_by";

    /**
     * column create_at
     */
    public static final String COLUMN_CREATE_AT = "create_at";

    /**
     * column update_by
     */
    public static final String COLUMN_UPDATE_BY = "update_by";

    /**
     * column update_at
     */
    public static final String COLUMN_UPDATE_AT = "update_at";

    /**
     * column reason
     */
    public static final String COLUMN_REASON = "reason";

    /**
     * AS 两边空格
     */
    public static final String AS = " AS ";

    /**
     * 模板 type
     */
    public static final String TEMPLATE_TYPE = "type";

    /**
     * 模板 key
     */
    public static final String TEMPLATE_KEY = "key";

    /**
     * 模板 remark
     */
    public static final String TEMPLATE_REMARK = "remark";

    /**
     * 模板 rule
     */
    public static final String TEMPLATE_RULE = "rule";

    /**
     * 模板 required
     */
    public static final String TEMPLATE_REQUIRED = "required";

    /**
     * 模板 regex
     */
    public static final String TEMPLATE_REGEX = "regex";

    /**
     * 模板 VARCHAR
     */
    public static final String TEMPLATE_VARCHAR = "VARCHAR";

    /**
     * 企业充值金额列表 单位：分
     */
    public static final List<Long> ENTERPRISE_RECHARGE_AMOUNT_LIST = Arrays.asList(10000L, 20000L, 30000L, 50000L,
            100000L, 200000L, 300000L, 500000L,
            1000000L, 2000000L, 3000000L, 5000000L);


    /**
     * 支付宝支付产品码
     */
    public static final String ALIPAY_PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

}
