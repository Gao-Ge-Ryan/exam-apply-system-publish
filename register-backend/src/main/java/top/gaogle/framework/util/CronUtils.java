package top.gaogle.framework.util;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * cron表达式工具类
 *
 * @author gaogle
 */
public class CronUtils {
    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 返回一个字符串值,表示该消息无效Cron表达式给出有效性
     *
     * @param cronExpression Cron表达式
     * @return String 无效时返回表达式错误描述,如果有效返回null
     */
    public static String getInvalidMessage(String cronExpression) {
        try {
            new CronExpression(cronExpression);
            return null;
        } catch (ParseException pe) {
            return pe.getMessage();
        }
    }

    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return Date 下次Cron表达式执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 将时间戳转换为 Quartz Cron 表达式
     *
     * @param timestamp 时间戳，单位为毫秒
     * @return Quartz Cron 表达式
     */
    public static String convertTimestampToCron(long timestamp) {
        // 将时间戳转换为 Date 对象
        Date date = new Date(timestamp);

        // 使用 Calendar 获取时间信息
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar 的月份从0开始
        int year = calendar.get(Calendar.YEAR);

        // 构建 Quartz Cron 表达式
        return String.format("%d %d %d %d %d ? %d", second, minute, hour, day, month, year);
    }

}
