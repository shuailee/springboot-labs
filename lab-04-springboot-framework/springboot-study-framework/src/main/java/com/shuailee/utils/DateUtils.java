package com.shuailee.utils;


import com.shuailee.exception.exceptiontype.SysException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author gerry
 * @Description 时间日期处理
 * @Date
 */
public final class DateUtils {

    private DateUtils() {
    }

    private static final String DATE_STRING_NOT_NULL = "The 'dateString' must not be null!";
    private static final String DATE_NOT_NULL = "The 'date' must not be null!";

    /**
     * 默认日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认时间格式：yyyyMM
     */
    public static final String YYYYMM_DATE_PATTERN = "yyyyMM";

    /**
     * 默认时间格式：yyyyMMdd
     */
    public static final String YYYYMMDD_DATE_PATTERN = "yyyyMMdd";

    /**
     * 默认时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 取得系统当前年份
     *
     * @return
     */
    public static int currentYear() {
        return DateTime.now().getYear();
    }

    /**
     * 取得当前系统日期
     *
     * @return
     */
    public static Date currentDate() {
        return DateTime.now().toDate();
    }

    /**
     * 取得系统当前日期，返回默认日期格式的字符串。
     *
     * @param strFormat
     * @return
     */
    public static String nowDate(String strFormat) {
        Assert.notNull(strFormat, DATE_STRING_NOT_NULL);
        return new DateTime().toString(strFormat, Locale.CHINESE);
    }

    /**
     * 取得当前系统时间戳
     *
     * @return
     */
    public static Timestamp currentTimestamp() {
        return new Timestamp(new DateTime().getMillis());
    }

    /**
     * 将日期字符串转换为java.util.Date对象
     *
     * @param dateString
     * @param pattern    日期格式
     * @return
     * @throws Exception
     */
    public static Date toDate(String dateString, String pattern) {
        Assert.notNull(dateString, DATE_STRING_NOT_NULL);
        Assert.notNull(pattern, "The 'pattern' must not be null!");
        return DateTime.parse(dateString, DateTimeFormat.forPattern(pattern)).toDate();
    }

    /**
     * 将日期字符串转换为java.util.Date对象
     *
     * @param dateString example:"20XX-05-03T15:11:45.7009265+08:00"
     * @return
     * @throws Exception
     */
    public static Date toISODate(String dateString) {
        Assert.notNull(dateString, DATE_STRING_NOT_NULL);
        return DateTime.parse(dateString, ISODateTimeFormat.dateTime()).toDate();
    }

    /**
     * 将日期字符串转换为java.util.Date对象，使用默认日期格式
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date toDate(String dateString) {
        Assert.notNull(dateString, DATE_STRING_NOT_NULL);
        return DateTime.parse(dateString).toDate();
    }

    /**
     * 将时间字符串转换为java.util.Date对象
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date toDateTime(String dateString) {
        Assert.notNull(dateString, DATE_STRING_NOT_NULL);
        return DateTime.parse(dateString, DateTimeFormat.forPattern(DEFAULT_DATETIME_PATTERN)).toDate();
    }

    /**
     * 将java.util.Date对象转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toDateString(Date date, String pattern) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(pattern, "The 'pattern' must not be null!");
        return new DateTime(date).toString(pattern, Locale.CHINESE);
    }

    /**
     * 将java.util.Date对象转换为字符串，使用默认日期格式
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        Assert.notNull(date, DATE_NOT_NULL);
        return new DateTime(date).toString(DEFAULT_DATE_PATTERN, Locale.CHINESE);
    }

    /**
     * 将java.util.Date对象转换为时间字符串，使用默认日期格式
     *
     * @param date
     * @return
     */
    public static String toDateTimeString(Date date) {
        Assert.notNull(date, DATE_NOT_NULL);
        return new DateTime(date).toString(DEFAULT_DATETIME_PATTERN, Locale.CHINESE);
    }

    /**
     * 日期相减
     *
     * @param date
     * @param days
     * @return
     */
    public static Date diffDate(Date date, Integer days) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(days, "The 'days' must not be null!");
        return new DateTime(date).minusDays(days).toDate();

    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Assert.notNull(date, DATE_NOT_NULL);
        return new DateTime(date).getMillis();
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param days 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, Integer days) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(days, "The 'days' must not be null!");
        return new DateTime(date).plusDays(days).toDate();
    }

    /**
     * 日期增加年数
     *
     * @param date
     * @param years
     * @return
     */
    public static Date addYear(Date date, Integer years) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(years, DATE_STRING_NOT_NULL);
        return new DateTime(date).plusYears(years).toDate();
    }

    /**
     * 日期增加月数
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonth(Date date, Integer months) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(months, DATE_STRING_NOT_NULL);
        return new DateTime(date).plusMonths(months).toDate();
    }

    /**
     * 日期增加小时
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, Integer hours) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(hours, "The 'hours' must not be null!");
        return new DateTime(date).plusHours(hours).toDate();
    }

    /**
     * 日期增加分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, Integer minutes) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(minutes, "The 'minutes' must not be null!");
        return new DateTime(date).plusMinutes(minutes).toDate();
    }

    /**
     * 日期增加秒
     *
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, Integer seconds) {
        Assert.notNull(date, DATE_NOT_NULL);
        Assert.notNull(seconds, "The 'seconds' must not be null!");
        return new DateTime(date).plusSeconds(seconds).toDate();
    }

    /**
     * 根据季度获得相应的月份
     *
     * @param quarters 季度
     * @return 返回相应的月份
     */
    public static String getMonth(String quarters) {
        Assert.notNull(quarters, "The 'quarters' must not be null!");
        String month;
        int m = Integer.parseInt(quarters);
        m = m * 3 - 2;
        if (m > 0 && m < 10) {
            month = "0" + String.valueOf(m);
        } else {
            month = String.valueOf(m);
        }
        return month;
    }

    /**
     * 根据月份获得相应的季度
     * @param month 月份
     * @return 返回相应的季度
     */
    public static String getQuarters(String month) {
        Assert.notNull(month, "The 'month' must not be null!");
        String quarters = null;
        int m = Integer.parseInt(month);
        if (m == 1 || m == 2 || m == 3) {
            quarters = "1";
        }
        if (m == 4 || m == 5 || m == 6) {
            quarters = "2";
        }
        if (m == 7 || m == 8 || m == 9) {
            quarters = "3";
        }
        if (m == 10 || m == 11 || m == 12) {
            quarters = "4";
        }
        return quarters;
    }

    /**
     * 获取日期所在星期的第一天，这里设置第一天为星期日
     * @param datestr
     * @return
     */
    public static String getFirstDateOfWeek(String datestr) {
        Assert.notNull(datestr, DATE_STRING_NOT_NULL);
        DateTime dt = DateTime.parse(datestr);
        return dt.plusDays(-(dt.getDayOfWeek()) + 1).toString(DEFAULT_DATE_PATTERN);
    }

    /**
     * 获取日期所在当年的第几周
     *
     * @param datestr
     * @return
     */
    public static int getWeekOfYear(String datestr) {
        Assert.notNull(datestr, DATE_STRING_NOT_NULL);
        return DateTime.parse(datestr).weekOfWeekyear().get();
    }

    /**
     * 通过日期字符串yyyy-MM-dd HH:mm:ss 获取星期
     *
     * @param datestr
     * @return
     */
    public static String getWeekday(String datestr) {
        Assert.notNull(datestr, DATE_STRING_NOT_NULL);
        try {
            switch (DateTime.parse(datestr).dayOfWeek().get()) {
                case 1:
                    return "星期一";
                case 2:
                    return "星期二";
                case 3:
                    return "星期三";
                case 4:
                    return "星期四";
                case 5:
                    return "星期五";
                case 6:
                    return "星期六";
                default:
                    return "星期天";
            }
        } catch (Exception ex) {
            throw new SysException(ex);
        }

    }

    public static Date getDate(Object object) {
        Assert.notNull(object, "The 'object' must not be null!");
        if (object instanceof String) {
            return DateTime.parse((String) object).toDate();
        } else if (object instanceof Date || object instanceof Timestamp) {
            return (Date) object;
        } else if (object instanceof Long) {
            return new DateTime((Long) object).toDate();
        } else {
            throw new SysException("this object can't to date!");
        }
    }

    public static Date fromTimeticks(Long ticks) {
        Assert.notNull(ticks, "The 'ticks' must not be null!");
        return new DateTime(ticks).toDate();
    }

    public static Long toTimeticks(Date time) {
        return time.getTime();
    }

    /**
     * 获取UTC时间戳（以秒为单位）
     */
    public static int getTimestampInSeconds() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取UTC时间戳（以毫秒为单位）
     */
    public static long getTimestampInMillis() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return cal.getTimeInMillis();
    }

    public static long getSecondsFromNowToTomorrow(){
        Date currentDate = new Date();
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }
}
