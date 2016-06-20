package com.yunjing.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) throws Exception{
		String tdEles = " 2015/11/24";
		String s = tdEles.replaceAll("/", "").trim();
		System.out.println(s);
	}
	
	public static String testBlack(String str){
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String dest = m.replaceAll("");
		return dest;
	}
	
	
	public static void testCollections(){
//		List< String> list = ["item"];
		
	}
	
	public static String getDateBefore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();    cal.add(Calendar.YEAR, -2);
        return sdf.format(cal.getTime());
    }

	

}
