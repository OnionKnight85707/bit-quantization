package com.mzwise.common.util;

import com.mzwise.constant.DateUnitEnum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {
    public static final DateFormat SS = new SimpleDateFormat("ss");
    public static final DateFormat mm = new SimpleDateFormat("mm");
    public static final DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
    public static final DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");

    public static final DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static final DateFormat YYYY_MM_DD_MM_HH_SS_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static final DateFormat YYYYMMDDMMHHSSSS = new SimpleDateFormat("yyyyMMddHHmmssSS");

    public static final DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    public static String dateToString(Date date) {
        return YYYY_MM_DD_MM_HH_SS.format(date);
    }

    public static String dateToStringDate(Date date) {
        return YYYY_MM_DD.format(date);
    }

    public static String dateToStringLengthIs17(Date date) {
        return YYYYMMDDMMHHSSSSS.format(date);
    }

    public static String dateTo3S(Date date) {
        return YYYY_MM_DD_MM_HH_SS_SSS.format(date);
    }

    /**
     * @param date1
     * @param date2
     * @return 1 大于 -1 小于 0 相等
     */
    public static int compare(Date date1, Date date2) {
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当时日期时间串 格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateTime() {
        return YYYY_MM_DD_MM_HH_SS.format(new Date());
    }


    /**
     * 获取当前分钟数
     *
     * @return
     */
    public static Integer getMinute() {
        return Integer.valueOf(mm.format(new Date()));
    }

    /**
     * 获取当前秒数
     *
     * @return
     */
    public static Integer getSecond() {
        return Integer.valueOf(SS.format(new Date()));
    }

    public static Date getStringToDate3S(String dateString) {
        try {
            return YYYYMMDDMMHHSSSSS.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getStringToDate2S(String dateString) {
        try {
            return YYYYMMDDMMHHSSSS.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当时日期串 格式 yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        return YYYY_MM_DD.format(new Date());
    }

    public static String getDateYMD() {
        return YYYYMMDD.format(new Date());
    }

    public static String getDateYMD(Date date) {
        return YYYYMMDD.format(date);
    }

    public static Date strToDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strToYYMMDDDate(String dateString) {
        Date date = null;
        try {
            date = YYYY_MM_DD.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取日期相差天数(考虑时分秒)
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        long days = 0L;
        long start = startDate.getTime();
        long end = endDate.getTime();
        days = (end - start) / 86400000L;
        return days;
    }

    /**
     * 获取日期相差天数(不考虑时分秒)
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDifferenceDays(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int dayOfYearBegin = beginCalendar.get(Calendar.DAY_OF_YEAR);
        int dayOfYearEnd = endCalendar.get(Calendar.DAY_OF_YEAR);
        return dayOfYearEnd - dayOfYearBegin;
    }

    public static Date dateAddMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    public static java.sql.Date dateAddDay(java.sql.Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 根据间隔加时间
     *
     * @param date
     * @param day
     * @param unit @see DateUnitEnum
     * @return
     */
    public static Date changeDateByUnit(Date date, int day, DateUnitEnum unit) {
        if (unit.equals(DateUnitEnum.MINUTE)) { // 分钟
            return add(date, Calendar.MINUTE, day);
        } else if (unit.equals(DateUnitEnum.HOUR)) { // 日
            return add(date, Calendar.HOUR_OF_DAY, day);
        } else if (unit.equals(DateUnitEnum.WEEK)) { // 周
            day = day * 7;
            return add(date, Calendar.DAY_OF_YEAR, day);
        } else if (unit.equals(DateUnitEnum.MONTH)) { // 月
            return add(date, Calendar.MONTH, day);
        } else if (unit.equals(DateUnitEnum.YEAR)) { // 年
            return add(date, Calendar.YEAR, day);
        } else { // 日
            return add(date, Calendar.DAY_OF_YEAR, day);
        }
    }

    /**
     * 毫秒
     *
     * @param date
     * @param day
     * @param unit
     * @return
     */
    public static Long changeTimestampByUnit(Date date, int day, DateUnitEnum unit) {
        Date date1 = changeDateByUnit(date, day, unit);
        return date1.getTime();
    }

    /**
     * 秒
     *
     * @param date
     * @param day
     * @param unit
     * @return
     */
    public static Long changeTimestampSecoundByUnit(Date date, int day, DateUnitEnum unit) {
        Long time = changeTimestampByUnit(date, day, unit);
        return time / 1000;
    }

    /**
     * 计算天数
     *
     * @param day
     * @param period 0：日，1：周，2：月，3：年
     * @return
     */
    public static int dayByPeriod(int day, int period) {
        if (period == 1) { // 周
            return day * 7;
        } else if (period == 2) { // 月
            return day * 30;
        } else if (period == 3) { // 年
            return day * 365;
        } else { // 日
            return day;
        }
    }

    public static Date dateAddYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    public static Date dateAddMin(Date date, int min) {
        return add(date, Calendar.MINUTE, min);
    }

    public static String dateAddDay(String dateString, int day) {
        Date date = strToYYMMDDDate(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return YYYY_MM_DD.format(calendar.getTime());
    }

    public static String dateAddDay(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return YYYY_MM_DD.format(calendar.getTime());
    }

    public static String dateAddMonth(int month) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return YYYY_MM.format(calendar.getTime());
    }

    public static String remainDateToString(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        if (endDate == null) {
            return "过期";
        }
        long times = endDate.getTime() - startDate.getTime();
        if (times < -1L) {
            result.append("过期");
        } else {
            long temp = 86400000L;

            long d = times / temp;

            times %= temp;
            temp /= 24L;
            long m = times / temp;

            times %= temp;
            temp /= 60L;
            long s = times / temp;

            result.append(d);
            result.append("天");
            result.append(m);
            result.append("小时");
            result.append(s);
            result.append("分");
        }
        return result.toString();
    }

    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    private static java.sql.Date add(java.sql.Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        Date time = calendar.getTime();
        return new java.sql.Date(time.getTime());
    }

    /**
     * 获得近几天的日期，YY-mm-dd表示(包含今天)
     *
     * @param before
     * @return
     */
    public static List<String> getLastDays(int before) {
        ArrayList<String> re = new ArrayList<>();
        for (int i = before - 1; i >= 0; i--) {
            re.add(dateAddDay(-i));
        }
        return re;
    }

    /**
     * 获得近几个月的日期，YY-mm表示(包含本月)
     *
     * @param before
     * @return
     */
    public static List<String> getLastMons(int before) {
        ArrayList<String> re = new ArrayList<>();
        for (int i = before - 1; i >= 0; i--) {
            re.add(dateAddMonth(-i));
        }
        return re;
    }

    public static String getLinkUrl(boolean flag, String content, String id) {
        if (flag) {
            content = "<a href='finance.do?id=" + id + "'>" + content + "</a>";
        }
        return content;
    }

    public static long getTimeCur(String format, String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    public static long getTimeCur(String format, Date date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time).longValue();
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }


    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getFormatTime(DateFormat format, Date date) throws ParseException {
        return format.format(date);
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getWeekDay(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        switch (dayOfWeek) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    public static String toGMTString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static int getDatePart(Date date, int part) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(part);
    }

    public static Date getDate(Date date, int day) {

        synchronized (YYYY_MM_DD) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -day);
            date = calendar.getTime();
            try {
                return YYYY_MM_DD.parse(YYYY_MM_DD.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Date getDateNoTime(Date curDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取周期性时间的起始时间和结束时间之间的时间(包含结束时间)，如24小时，一周7天
     *
     * @param range 周期最大值 周：7, 小时：24
     * @param start
     * @param end
     * @return
     * @author wmf
     * @errorm 错误返回空
     */
    public static List<Integer> getWithinNums(int range, int start, int end) {
        List<Integer> days = new ArrayList<>();
        for (int i = 0; i < range * 2; i++) {
            int d = i;
            if (i >= range) {
                d -= range;
            }
            days.add(d);
        }
        System.out.println(days);
        if (!days.contains(start) || !days.contains(end)) {
            return null;
        }
        ArrayList<Integer> activeList = new ArrayList<>();
        Boolean trigger = false;
        for (Integer day : days) {
            if (trigger) {
                activeList.add(day);
                if (day == end) {
                    break;
                }
            }
            if (day == start) {
                trigger = true;
                activeList.add(day);
                if (day == end) {
                    break;
                }
            }
        }
        return activeList;
    }

    /**
     * 返回当天0点时间
     */
    public static Date getZeroTimeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getZeroTimeNDaysBefore(Date beginDate, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 判断当前时间是否处在某时间段范围内
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean withinActivity(Date beginTime, Date endTime) {
        Calendar now = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTime(beginTime);
        end.setTime(endTime);
        boolean flag = false;
        if (begin.before(now) && end.after(now)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断两个时间大小
     * @param oneTime
     * @param twoTime
     * @return
     */
    public static boolean whichBig (Date oneTime, Date twoTime) {
        boolean flag = false;
        Calendar one = Calendar.getInstance();
        Calendar two = Calendar.getInstance();
        one.setTime(oneTime);
        two.setTime(twoTime);
        if (one.before(two)) {
            flag = true;
        }
        return flag;
    }

    public static String getNowStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 距离凌晨时间
     * @return 距离凌晨的秒数
     * @throws ParseException
     */
    public static long beforeMidnight () throws ParseException {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
        long overTime = (now - (sdfOne.parse(sdfOne.format(now)).getTime()))/1000;
        //当前毫秒数
//        System.out.println("当前毫秒数"+now);
        //当前时间  距离当天凌晨  秒数 也就是今天过了多少秒
//        System.out.println("今天过了多少秒"+overTime);
        //当前时间  距离当天晚上23:59:59  秒数 也就是今天还剩多少秒
        long timeNext = 24*60*60 - overTime;
//        System.out.println("今天还剩多少秒"+timeNext);
        return timeNext;
    }

    public static void main(String[] args) throws Exception {
        String startTime = "2022-07-25 00:00:00";
        String endTime = "2022-09-25 12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse(startTime);
        Date endDate = sdf.parse(endTime);
//        System.out.println(withinActivity(startDate, endDate));
        System.out.println(whichBig(startDate, endDate));
        System.out.println("getNowStr: " + getNowStr());
    }

}