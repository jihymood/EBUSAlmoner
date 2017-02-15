package com.xpro.ebusalmoner.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @ClassName: DateUtils
 * @Description: date处理工具类
 * @author: houyang
 * @date: 2016年9月23日 上午10:52:16
 */
public class DateUtils {
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
		Date date = new Date();// ȡʱ��
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// ��������������һ��.����������,������ǰ�ƶ�
		date = calendar.getTime(); // ���ʱ���������������һ��Ľ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		System.out.println(dateString);
		return dateString;
	}

	public static String getDate() {
		Calendar now = Calendar.getInstance();
		return String.valueOf(now.get(Calendar.DAY_OF_MONTH));
	}

	public static String getM() {
		Calendar now = Calendar.getInstance();
		return String.valueOf((now.get(Calendar.MONTH) + 1));
	}

	public static String getY() {
		Calendar now = Calendar.getInstance();
		return String.valueOf(now.get(Calendar.YEAR));
	}
}
