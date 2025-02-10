package top.gaogle.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author gaogle
 */
@Component
@ConfigurationProperties(prefix = "gaogle")
public class GaogleConfig {
    /** 获取地址开关 */
    private static boolean addressEnabled;

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }
}
