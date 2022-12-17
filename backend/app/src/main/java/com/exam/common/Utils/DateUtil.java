package com.exam.common.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期管理工具
 */
public class DateUtil {
    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis;
    }

    /**
     * 6      * 时间戳转换成日期格式字符串
     * 7      * @param seconds 精确到秒的字符串
     * 8      * @param formatStr
     * 9      * @return
     * 10
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }
}
