package top.gaogle.framework.util;

import java.util.Base64;

public class SecretUtil {

    public static String getEncoderByBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String getDecoderByBase64(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }

}
