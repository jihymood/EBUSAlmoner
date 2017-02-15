package com.xpro.ebusalmoner.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by l on 2016/3/25.
 */
public class DateUtil {

    public static boolean isDateAfter(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2))
                return df.parse(date1).after(df.parse(date2));
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String toMMddString(String data) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.format(df.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getYMDate() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往前推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(date);
        System.out.println(dateString);
        return dateString;
    }

    /**
     * 获得当前日
     *
     * @return
     */
    public static String getDate() {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获得当前月
     *
     * @return
     */
    public static String getM() {
        Calendar now = Calendar.getInstance();
        return String.valueOf((now.get(Calendar.MONTH) + 1));
    }

    /**
     * 获得当前年
     *
     * @return
     */
    public static String getY() {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.YEAR));
    }

    /**
     * 获取当前系统时间
     * 2016-11-01 15：00：24
     *
     * @return
     */
    public static String getYMDDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 获得当天星期几
     *
     * @return
     */
    public static String getDay() {
        Date date = new Date();

        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");

        return dateFm.format(date);
    }

    public static String getDateYYMMDD() {
        Date dt = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
        return matter1.format(dt);
    }


    public static String getDateYYMMDD(long mile) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mile);
        return formatter.format(calendar.getTime());
    }


    /**
     * 15:08:20
     *
     * @return
     */
    public static String getHour() {
        Calendar time = Calendar.getInstance();
        time.get(Calendar.HOUR_OF_DAY);//获取小时
        time.get(Calendar.MINUTE);//获取分钟
        time.get(Calendar.SECOND);//获取秒
        return time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND);
    }

    /**
     * 根据日期获得为星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 获得周一的日期
     */
    public static String getMonday(int flag) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time = null;
        try {
            time = sdf.parse(getYMDDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(time);

        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天


        System.out.println(cal.getFirstDayOfWeek() + "-" + day + "+6=" + (cal.getFirstDayOfWeek() - day + 6));


//        switch (flag) {
//            //获得周一的日期
//            case Constants.GETMONDAYDATE:
//                cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//                return sdf.format(cal.getTime());
//
//            //获得周日的日期
//            case Constants.GETSUNDAYDATE:
//                cal.add(Calendar.DATE, 6);
//                return sdf.format(cal.getTime());
//        }
        return "1970-01-01";
    }
}
