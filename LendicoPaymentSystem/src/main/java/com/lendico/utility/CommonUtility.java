package com.lendico.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtility {
	
	static String defaultPattern = "MM-dd-yyyy"; 

	public static Date getNextMonth(Date date, int count) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, count);
		return cal.getTime();
	}

	public static double getUptoTwoDecimal(double value) {
		return Math.round((value) * 100.0) / 100.0;
	}
	
	public static String get_MM_DD_YY_StringDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern);
		return simpleDateFormat.format(date);
	}
	

}
