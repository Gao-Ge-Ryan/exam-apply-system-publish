package top.gaogle.framework.util;


import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.gaogle.common.RegisterConst;
import top.gaogle.framework.config.GaogleConfig;

/**
 * 获取地址类
 *
 * @author gaogle
 */
public class AddressUtil {
    private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            return "内网IP";
        }
        if (GaogleConfig.isAddressEnabled()) {
            try {
                String rspStr = HttpUtil.sendGet(IP_URL, "ip=" + ip + "&json=true", RegisterConst.GBK);
                if (StringUtil.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                ObjectNode obj = JsonUtil.parseObjectNode(rspStr);
                String region = obj.get("pro").asText();
                String city = obj.get("city").asText();
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return UNKNOWN;
    }
}
