package com.yunjing.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) throws Exception{
//		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml","aaa.xml"});
//		ac.getBean("tet");
		
		
//		String xml = " 页次 1/  4  共47条记录";
//		String page = xml.split("/")[1].split("共")[0];
//		System.out.println(page);
//		System.out.println(Integer.parseInt(page.trim()));
		
//		String payDate = "20140909afafea";
//		System.out.println(URLEncoder.encode(payDate, "utf-8"));
		
//		System.out.println(getDateBefore());
		String str = "\n	\t\t\n	\t\t\n	\t\t操作出错!\n	\t\t\n	\t\t验证码错误";
		System.out.println(str.replaceAll("[\b\r\n\t]*", ""));
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
