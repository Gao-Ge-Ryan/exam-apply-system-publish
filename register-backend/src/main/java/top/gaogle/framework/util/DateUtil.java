package top.gaogle.framework.util;


import top.gaogle.common.RegisterConst;
import top.gaogle.pojo.dto.TimeAfterMinutesDTO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具
 *
 * @author gaoge
 * @since 1.0.0
 */
public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException(RegisterConst.PROHIBIT_INSTANTIATION);
    }

    /**
     * 获取当前时间的秒数（从1970年1月1日开始的秒数，也称为Unix时间戳）
     *
     * @return currentSeconds 当前时间秒数
     */
    public static Long currentSeconds() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 获取当前时间的毫秒（从1970年1月1日开始的秒数，也称为Unix时间戳）
     *
     * @return currentSeconds 当前时间秒数
     */
    public static Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 将时间戳格式化
     */
    public static String timeMillisFormatter(Long timeMillis, String formatter) {
        // 转换为 LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.systemDefault());

        // 定义时间格式为 "HH:mm"
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);

        // 格式化为 "09:00" 格式
        return dateTime.format(dateTimeFormatter);
    }

    /**
     * 返回加小时数日期
     *
     * @param hoursToAdd 加小时数
     * @return Date格式
     */
    public static Date getDatePlusHour(int hoursToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        return calendar.getTime();
    }

    /**
     * 获取当前时间加指定分钟数后的时间，格式为yyyy-MM-dd HH:mm:ss。
     *
     * @param minutes 要增加的分钟数
     * @return 格式化后的时间字符串
     */
    public static TimeAfterMinutesDTO getTimeDTOAfterMinutes(int minutes) {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 增加指定的分钟数
        calendar.add(Calendar.MINUTE, minutes);
        // 获取新的时间
        Date newTime = calendar.getTime();
        // 格式化时间为指定格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeAfterMinutesDTO minutesDTO = new TimeAfterMinutesDTO();
        minutesDTO.setTimestamp(newTime.getTime());
        minutesDTO.setFormat(sdf.format(newTime));
        return minutesDTO;
    }

    /**
     * 获取当前时间加指定分钟数后的时间，格式为yyyy-MM-dd HH:mm:ss。
     *
     * @param minutes 要增加的分钟数
     * @return 格式化后的时间字符串
     */
    public static String getTimeAfterMinutes(int minutes) {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 增加指定的分钟数
        calendar.add(Calendar.MINUTE, minutes);
        // 获取新的时间
        Date newTime = calendar.getTime();
        // 格式化时间为指定格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(newTime);
    }

}
