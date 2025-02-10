package top.gaogle.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具
 *
 * @author gaoge
 * @since 1.0.0
 */
public class ValidatorUtil {


    public static Boolean regex(String regex, String value) {
        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);
        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(value);
        // 使用 find() 方法查找匹配
        return matcher.find();
    }

}
