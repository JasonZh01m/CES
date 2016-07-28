package com.sq.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil {
	private String year;
	private String month;
	private String day;

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public static String getDateTime() {
		String requestDate = "";
		DateTime dateTime = new DateTime();
		requestDate = dateTime.toString("yyyy-MM-dd HH:mm:ss".intern());
		return requestDate;
	}

	public void calendarUtil(String source_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (source_date == "") {
			source_date = sdf.format(new Date());
		}
		try {
			Date date = sdf.parse(source_date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// Modified By JasonZh 0728
			// setYear(cal.get(1));
			// setMonth(cal.get(2) + 1);
			// setDay(cal.get(5));
			setYear(cal.get(1) + "");
			setMonth((cal.get(2) + 1) + "");
			setDay(cal.get(5) + "");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DateUtil du = new DateUtil();
		du.calendarUtil("2014-09-23");
		System.out.println(du.getYear());
	}
}
