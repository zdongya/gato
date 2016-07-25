package com.yunjing.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.yunjing.dto.PushDto;

public class Utils {
	public static final String KEY = "YUNJINGAPP";
	public static final int OVERTIME_DAY = 7; //token过期时间为7天
	private static Logger logger = Logger.getLogger(Utils.class);
	public static final String UPLOAD_IMG_DIR = "/opt/export/upload/img"; //正式环境
	public static final String ANDROID_UPGRADE = "/opt/export/upload/file/config/android_upgrade.xml";
	public static final String IOS_UPGRADE = "/opt/export/upload/file/config/ios_upgrade.xml";
	public static final String BANNER_VERSION = "/opt/export/upload/file/config/banner_upgrade.xml";
	public static final String INTERKEY = "yunjing";
	public static final String IMG_URL = "http://115.159.44.248:8085/";

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 创建token
	 * @param appId
	 * @param userId
	 * @return
	 */
	public static String createToken(String appId, String userId){
		long timeStamp = System.currentTimeMillis();
		String toSignStr = appId + userId + timeStamp + KEY;
		return getMD5Str(toSignStr);
	}
	
	
	public static String getMD5Str(String source) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			byte[] byteArray = messageDigest.digest(source.getBytes("UTF-8"));
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			return md5StrBuff.toString();
		} catch (Exception e) {
			logger.info("对字符串进行MD5加密发生异常,source" + source + ",异常消息:" + e.getMessage());
			return null;
		}
	}
	
	public static String getTokenOverTime(){
		Calendar c=Calendar.getInstance();   
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		c.setTime(new Date());   
		c.add(Calendar.DATE, OVERTIME_DAY);   
		Date d2=c.getTime();   
		String s=df.format(d2);   
		return s;  
	}
	
	/**
	 * 获取app版本号
	 * @param appVersion
	 * @return
	 */
	public static int getVersion(String appVersion){
		if (CheckUtil.isNullString(appVersion)){
			return -1;
		} else {
			String[] appVersions = appVersion.split("\\.");
			return Integer.parseInt(appVersions[0]) *100 + Integer.parseInt(appVersions[1]) *10 + Integer.parseInt(appVersions[2]);
		}
	}
	
	
	public static Object convertMap(Class type, Map<String, Object> map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

	public static String getSignStr(Map<String, String> params, String signKey) {
		List<String> lists = new ArrayList<String>(params.keySet());
		Collections.sort(lists);
		String callparams = "";
		for (int i = 0; i < lists.size(); i++) {
			String key = lists.get(i).toString();
			String value = params.get(key).toString();
			callparams += value;
		}
		return callparams + signKey;
	}
	

	public static String wrapPushMsg(PushDto pushDto) {
		Map<String, String> map = new HashMap<String, String>();
		String action = "defence"; //默认布撤防
		if (pushDto.getItype().equals("0")){ //消警
			action = "cleanup";
			map.put("warningId", pushDto.getWarningId());
		}
		map.put("action", action);
		map.put("time", pushDto.getTime());
		map.put("msgId", pushDto.getMsgId());
		map.put("istate", pushDto.getCommandState());
		map.put("zoneNo", pushDto.getZoneNo());
		String toSignStr = Utils.getSignStr(map, Utils.INTERKEY);
		logger.info("tosignStr:" + toSignStr);
		String serverSign = Utils.getMD5Str(toSignStr);
		map.put("sign", serverSign);
		String value = JSONObject.toJSONString(map);
		return value;
		
	}
	
	
	/**
	 * 邮箱地址验证
	 * @author xhs
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-z0-9A-Z]+[-_|.]*)+[a-z0-9A-Z]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 手机号码验证
	 * @author xhs
	 * @return boolean
	 */
	public static boolean isMobilephone(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	
	public static void main(String[] args) {
		System.out.println(getTokenOverTime());
		PushDto dto = new PushDto();
		
	}

}
