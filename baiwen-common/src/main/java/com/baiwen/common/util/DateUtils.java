package com.baiwen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
public class DateUtils {
    public static final String YYYYMMDDHHMMSSMS_W_C = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String YYYYMMDDHHMMSS_W_C = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMM_W_C = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHH_W_C = "yyyy-MM-dd HH";
    public static final String YYYYMMDD_W = "yyyy-MM-dd";
    public static final String YYYYMM_W = "yyyy-MM";
    public static final String YYYYMMDDHHMMSSMS = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String MMDD = "MMdd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String HHMMSS_C = "HH:mm:ss";
    public static final String HHMM_C = "HH:MM";
    public static final String HHMMSSAMPM_C = "HH:MM:SS AMPM";
    public static final String YYYYMMDD_CN = "yyyy年MM月dd日";
    public static final int MONTHS = 12;
    public static final int WEEKS = 7;
    public static final int HOURS = 24;
    public static final int MINUTES = 60;
    public static final int SECONDS = 60;
    public static final int MILLISECONDS = 1000;
    public static final String GMT = "EEE d MMM yyyy HH:mm:ss 'GMT'";

    public DateUtils() {
    }

    public static Date addMonths(Date date, int months) {
        return add(date, 2, months);
    }

    public static Date addDays(Date date, int days) {
        return add(date, 5, days);
    }

    public static Date addHours(Date date, int hours) {
        return add(date, 10, hours);
    }

    public static Date addMinutes(Date date, int minutes) {
        return add(date, 12, minutes);
    }

    public static Date addSeconds(Date date, int seconds) {
        return add(date, 13, seconds);
    }

    public static Date minusMonths(Date date, int months) {
        return add(date, 2, -1 * months);
    }

    public static Date minusDays(Date date, int days) {
        return add(date, 5, -1 * days);
    }

    public static Date minusHours(Date date, int hours) {
        return add(date, 10, -1 * hours);
    }

    public static Date minusMinutes(Date date, int minutes) {
        return add(date, 12, -1 * minutes);
    }

    public static Date minusSeconds(Date date, int seconds) {
        return add(date, 13, -1 * seconds);
    }

    private static Date add(Date date, int field, int amount) {
        Calendar cad = Calendar.getInstance();
        cad.setTime(date);
        cad.add(field, amount);
        return cad.getTime();
    }

    public static final long diffMilliseconds(Date dBefor, Date dAfter) {
        long lBefor = 0L;
        long lAfter = 0L;
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();
        return lAfter - lBefor;
    }

    public static final long diffSeconds(Date dBefor, Date dAfter) {
        return diffMilliseconds(dBefor, dAfter) / 1000L;
    }

    public static final long diffMinutes(Date dBefor, Date dAfter) {
        return diffMilliseconds(dBefor, dAfter) / 60L / 1000L;
    }

    public static final long diffHours(Date dBefor, Date dAfter) {
        return diffMilliseconds(dBefor, dAfter) / 60L / 60L / 1000L;
    }

    public static final long diffDays(Date dBefor, Date dAfter) {
        return diffMilliseconds(dBefor, dAfter) / 24L / 60L / 60L / 1000L;
    }

    public static final int diffMonths(Date dBefor, Date dAfter) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dBefor);
        int beforeMonths = calendar.get(2);
        int beforeYears = calendar.get(1);
        calendar.setTime(dAfter);
        int afterMonths = calendar.get(2);
        int afterYears = calendar.get(1);
        return (afterYears - beforeYears) * 12 + (afterMonths - beforeMonths);
    }

    public static final Date convertToDate(String dateStr, String format) {
        if (!StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(format)) {
            try {
                return getFormat(format).parse(dateStr);
            } catch (ParseException var3) {
                log.error("日期解析失败，日期不符合格式", var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public static final String convertToString(Date date, String format) {
        return date != null && !StringUtils.isEmpty(format) ? getFormat(format).format(date) : "";
    }

    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static final String convertToString(Date date, String format, TimeZone timeZone) {
        return date != null && !StringUtils.isEmpty(format) ? getFormat(format, timeZone).format(date) : "";
    }

    public static final String getCnFromUTC(Date date, String format) {
        return date != null && !StringUtils.isEmpty(format) ? getFormat(format, TimeZone.getTimeZone("GMT+8")).format(date) : "";
    }

    private static final DateFormat getFormat(String format, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat;
    }

    public static String getCurrentSeason() {
        return getCurrentSeason(new Date());
    }

    public static String getCurrentSeason(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(1);
        int currentMonth = calendar.get(2) + 1;
        if (currentMonth >= 1 && currentMonth <= 3) {
            return currentYear + "年第1季度";
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            return currentYear + "年第2季度";
        } else {
            return currentMonth >= 7 && currentMonth <= 9 ? currentYear + "年第3季度" : currentYear + "年第4季度";
        }
    }

    public static List<String> getCurrentSeasonMonth() {
        return getCurrentSeasonMonth(new Date());
    }

    public static List<String> getCurrentSeasonMonth(Date date) {
        List<String> ret = new ArrayList(5);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(1);
        int currentMonth = calendar.get(2) + 1;
        if (currentMonth >= 1 && currentMonth <= 3) {
            ret.add(currentYear + "-01");
            ret.add(currentYear + "-02");
            ret.add(currentYear + "-03");
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            ret.add(currentYear + "-04");
            ret.add(currentYear + "-05");
            ret.add(currentYear + "-06");
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            ret.add(currentYear + "-07");
            ret.add(currentYear + "-08");
            ret.add(currentYear + "-09");
        } else {
            ret.add(currentYear + "-10");
            ret.add(currentYear + "-11");
            ret.add(currentYear + "-12");
        }

        return ret;
    }

    public static int getQuarter() {
        return getQuarter(new Date());
    }

    public static int getQuarter(Date date) {
        String month = convertToString(date, "MM");
        int intmonth = Integer.parseInt(month);
        if (intmonth <= 3) {
            return 1;
        } else if (4 <= intmonth && intmonth <= 6) {
            return 2;
        } else {
            return 7 <= intmonth && intmonth <= 9 ? 3 : 4;
        }
    }

    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekint = calendar.get(3);
        return weekint;
    }

    public static boolean isWorkDay() {
        return isWorkDay(new Date());
    }

    public static boolean isWorkDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(7);
        return week != 1 && week != 7;
    }

    public static Date getMonthFirstDay() {
        return getMonthFirstDay(new Date());
    }

    public static Date getMonthFirstDay(Date date) {
        return getMonthDay(date, 0, 0);
    }

    public static Date getNextMonthFirstDay() {
        return getNextMonthFirstDay(new Date());
    }

    public static Date getNextMonthFirstDay(Date date) {
        return getMonthDay(date, 1, 0);
    }

    public static Date getPreviousMonthFirstDay() {
        return getPreviousMonthFirstDay(new Date());
    }

    public static Date getPreviousMonthFirstDay(Date date) {
        return getMonthDay(date, -1, 0);
    }

    public static Date getMonthLastDay() {
        return getMonthLastDay(new Date());
    }

    public static Date getMonthLastDay(Date date) {
        return getMonthDay(date, 1, -1);
    }

    public static Date getNextMonthLastDay() {
        return getNextMonthLastDay(new Date());
    }

    public static Date getNextMonthLastDay(Date date) {
        return getMonthDay(date, 2, -1);
    }

    public static Date getPreviousMonthLastDay() {
        return getPreviousMonthLastDay(new Date());
    }

    public static Date getPreviousMonthLastDay(Date date) {
        return getMonthDay(date, 0, -1);
    }

    private static Date getMonthDay(Date date, int monthOffset, int dayOffset) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(date);
        nowCal.set(5, 1);
        nowCal.add(2, monthOffset);
        nowCal.add(5, dayOffset);
        return nowCal.getTime();
    }

    public static Date getWeekFirstDay() {
        return getWeekFirstDay(new Date());
    }

    public static Date getWeekFirstDay(Date date) {
        return getWeekDay(date, 1, 0);
    }

    public static Date getNextWeekFirstDay() {
        return getNextWeekFirstDay(new Date());
    }

    public static Date getNextWeekFirstDay(Date date) {
        return getWeekDay(date, 1, 7);
    }

    public static Date getPreviousWeekFirstDay() {
        return getPreviousFirstDay(new Date());
    }

    public static Date getPreviousFirstDay(Date date) {
        return getWeekDay(date, 1, -7);
    }

    public static Date getWeekLastDay() {
        return getWeekLastDay(new Date());
    }

    public static Date getWeekLastDay(Date date) {
        return getWeekDay(date, 7, 0);
    }

    public static Date getNextWeekLastDay() {
        return getNextWeekLastDay(new Date());
    }

    public static Date getNextWeekLastDay(Date date) {
        return getWeekDay(date, 7, 7);
    }

    public static Date getPreviousWeekLastDay() {
        return getPreviousWeekLastDay(new Date());
    }

    public static Date getPreviousWeekLastDay(Date date) {
        return getWeekDay(date, 7, -7);
    }

    private static Date getWeekDay(Date date, int weekDay, int offset) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(date);
        nowCal.set(7, weekDay);
        nowCal.add(5, offset);
        return nowCal.getTime();
    }

    public static Date getDate(String zoneId) {
        if (!zoneId.equals(TimeZone.getDefault().getID())) {
            TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
        }

        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(15);
        int dstOffset = cal.get(16);
        cal.add(14, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    public static Date getUTCDate() {
        Date date = getDate("Asia/BeiJing");
        return date;
    }

    public static Date getCNDate() {
        Date date = getUTCDate();
        return addHours(date, 8);
    }

    public static Date getDate(Date date, int timezone) {
        return addHours(date, timezone);
    }
}
