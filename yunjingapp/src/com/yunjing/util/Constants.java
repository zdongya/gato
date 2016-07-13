package com.yunjing.util;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
	private static Map<Integer, String> smsTypeMap = new HashMap<Integer,String>();
	private static final String SERVICE = "yunxin";
	static {
		smsTypeMap.put(Integer.parseInt("0"), "验证码:RANDOM");
		smsTypeMap.put(Integer.parseInt("1"), "验证码:RANDOM");
	}

	public static String getSmsContentByType(int type, String yzm){
		String smsContent = smsTypeMap.get(new Integer(type));
		if (null != smsContent){
			smsContent = smsContent.replaceAll("RANDOM", yzm);
		}
		return smsContent;
	}
	
	public static String getSmsService(){
		return SERVICE;
	}
}
