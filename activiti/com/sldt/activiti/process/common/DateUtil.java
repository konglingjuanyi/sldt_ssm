/**
 * @author Administrator
 *
 */
package com.sldt.activiti.process.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date add(Date date, int calendarField, int amount) {
		if (date == null)
			throw new IllegalArgumentException("日期不能为空");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	public static String today() {
		return today("yyyy-MM-dd");
	}

	public static Date nowDate() {
		return new Date();
	}

	public static String now() {
		return today("yyyy-MM-dd HH:mm:ss");
	}

	public static String today(String pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("日期格式不能为空");
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dt = sdf.format(new Date());
		return dt;
	}

	public static Date parseDate(String src) {
		return parse(src, "yyyy-MM-dd");
	}

	public static Date parseDatetime(String src) {
		return parse(src, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parse(String src, String pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("日期格式不能为空");
		if (src == null)
			return null;
		try {
			return new SimpleDateFormat(pattern).parse(src);
		} catch (ParseException ex) {
			throw new IllegalArgumentException("日期格式转换出错,src=" + src + ",pattern=" + pattern);
		}
	}

	public static String formatDate(Date src) {
		if (src == null) {
			return "";
		}
		return format(src, "yyyy-MM-dd");
	}

	public static String formatDatetime(Date src) {
		if (src == null) {
			return "";
		}
		return format(src, "yyyy-MM-dd HH:mm:ss");
	}

	public static String format(Date src, String pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("日期格式不能为空");
		if (src == null)
			return null;
		return new SimpleDateFormat(pattern).format(src);
	}

	public static String addDate(int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(now.getTime());
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(now.getTime());
	}
}
