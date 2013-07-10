package com.gogofindit.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	private static int DAYS_CUT_OFF = 10;
	
	public static String getPastDate(){
		
		 DateFormat dateFormat = new SimpleDateFormat(SimpleDateFormatEnum.DATE.getSdfType());
         Date myDate = new Date(System.currentTimeMillis());
         System.out.println("result is "+ dateFormat.format(myDate));
         Calendar cal = Calendar.getInstance();
         cal.setTime(myDate);
         cal.add(Calendar.DATE, -DAYS_CUT_OFF);
         return dateFormat.format(cal.getTime());
         
	}

}
