package top.gaogle.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;

public class UrlUtil {
    public static String urlDecoder(String url) {

        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getIPv6ToIPv4(String ipAddress) {
        if (ipAddress.contains(":")) {
            try {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                if (inetAddress instanceof java.net.Inet6Address) {
                    // Convert IPv6 address to IPv4-compatible format
                    byte[] ipv4Bytes = new byte[4];
                    System.arraycopy(inetAddress.getAddress(), 12, ipv4Bytes, 0, 4);
                    return InetAddress.getByAddress(ipv4Bytes).getHostAddress();
                }
            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }
        }

        return ipAddress;
    }
}
