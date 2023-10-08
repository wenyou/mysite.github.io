package cn.stkit.wxl.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TimeUtil
 * @desc 日期时间工具类
 * @website http://www.stkit.cn/
 * @date 2020/6/20 2:00 下午
 */

public class TimeUtil
{
    /**
     * 取得当前时间戳（秒）
     * @return int 秒数
     */
    public static int getNowTimestamp()
    {
        long timestamp =  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        return Long.valueOf(timestamp).intValue();
    }

    /**
     * 取得date日期的前n day的日期
     * @param date
     * @param day
     * @return
     */
    public static Date getDateBefore(Date date, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        return calendar.getTime();
    }

    /**
     * 把时间戳格式化为字符串时间格式
     * @param timestamp int
     * @param fmt
     * @return
     */
    public static String getFmtDateTimeFromTimeStamp(Integer timestamp, String fmt)
    {
        LocalDateTime localDateTime = timestampToLocalDateTime(timestamp.longValue());

        if(fmt == null) {
            fmt = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);

        return localDateTime.format(formatter);
    }

    /**
     * 把时间戳格式化为字符串时间格式
     * @param timestamp  long
     * @param fmt
     * @return
     */
    public static String getFmtDateTimeFromTimeStamp(long timestamp, String fmt)
    {
        LocalDateTime localDateTime =  LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());;

        if(fmt == null) {
            fmt = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);

        return localDateTime.format(formatter);
    }

    /**
     * 时间戳（秒）转LocalDateTime
     * @param timestamp  seconds 秒，非毫秒
     * @return
     */
    public static LocalDateTime timestampToLocalDateTime(Long timestamp)
    {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000L), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转时间戳
     * @param localDateTime
     * @return
     */
    public static long localDateTimeToTimestamp(LocalDateTime localDateTime)
    {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

}
