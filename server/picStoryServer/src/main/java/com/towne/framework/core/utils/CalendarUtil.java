package com.towne.framework.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 字符串转成日期
	 * 
	 * @param inputStr
	 * @param format
	 * @return Date
	 */
	public static Date string2date(String inputStr, String format) {
		if (inputStr == null || inputStr.trim().equals(""))
			return null;
		if (format == null)
			format = "yyyy-MM-dd";
		inputStr = inputStr.trim();
		Date result = null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			result = dateFormat.parse(inputStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 日期转成字符串
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String date2string(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
