package com.yunjing.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	public static Date string2Date(String formate, String dateString) {
		SimpleDateFormat format = new SimpleDateFormat(formate, Locale.US);
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
		}
		return date;
	}

	public static Date StrFormate(String dateString) {
		return string2Date("yyyyMMdd", dateString);
	}

	public static Date jcalendar_Formate(String dateString) {
		return string2Date("dd/MM/yyyy HH:mm", dateString);
	}

	public static Date jcalendar_Formate_ymd(String dateString) {
		return string2Date("yyyy/MM/dd HH:mm", dateString);
	}

	private static Date ymd(String dateString) {
		return string2Date("yyyy-MM-dd HH:mm:ss", dateString);
	}

	public static Timestamp getNowTimestamp() {
		return new Timestamp(new GregorianCalendar().getTimeInMillis());
	}

	public static Timestamp getNowDateToTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static Timestamp getTimestampByString(String dateString) {
		return new Timestamp(ymd(dateString).getTime());
	}

	public static String Timestamp2String(Timestamp tem) {
		if (null == tem){
			return null;
		} else {
			return Timestamp2String("yyyy-MM-dd HH:mm:ss", tem);
		}
	}

	public static String Timestamp2String(String formate, Timestamp tem) {
		return (new SimpleDateFormat(formate)).format(tem);
	}

	public static Timestamp TimestampFormate(String formate, Timestamp tem) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		try {
			Date date = sdf.parse(sdf.format(tem));
			return new Timestamp(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Timestamp DateFormate(String formate, Date tem) {
		SimpleDateFormat format = new SimpleDateFormat(formate, Locale.US);
		Date date = null;
		try {
			date = format.parse(format.format(tem)); // Thu Jan 18 00:00:00 CST
														// 2007
		} catch (ParseException e) {
		}

		return new Timestamp(date.getTime());
	}

	public static String ToSBC(String input) {

		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String abbreviate(String str, int width, String ellipsis) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int d = 0; // byte length
		int n = 0; // char length
		for (; n < str.length(); n++) {
			d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;
			if (d > width) {
				break;
			}
		}

		if (d > width) {
			n = n - ellipsis.length() / 2;
			return str.substring(0, n > 0 ? n : 0) + ellipsis;
		}

		return str = str.substring(0, n);
	}

	public static long getDistDates(String start, String end)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(start);
		Date endDate = sdf.parse(end);
		return getDistDates(startDate, endDate);
	}

	public static Date getSubDate(Date date, int day) {
		long time1 = date.getTime();
		time1 = time1 + (day * 24 * 60 * 60 * 1000);
		date = new Date(time1);
		return date;
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	public static Date timestampToDate(Timestamp tt) {
		return new Date(tt.getTime());
	}
	
	  /**   
     * 返回两个日期相差的天数   
     * @param startDate   
     * @param endDate   
     * @return   
     * @throws ParseException   
     */   
    public static long getDistDates(Date startDate,Date endDate)      
    {    
        long totalDate = 0;    
        Calendar calendar = Calendar.getInstance();    
        calendar.setTime(startDate);    
        long timestart = calendar.getTimeInMillis();    
        calendar.setTime(endDate);    
        long timeend = calendar.getTimeInMillis();    
        totalDate = Math.abs((timeend - timestart))/(1000*60*60*24);    
        return totalDate;    
    }     
    
    
    public static String getNowDateTime(){
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	Date date = Calendar.getInstance().getTime();
    	return format.format(date);
    }
    


}
