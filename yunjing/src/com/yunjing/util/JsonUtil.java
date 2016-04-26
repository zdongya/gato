package com.yunjing.util;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunjing.entity.Zone;

public class JsonUtil {

	public static List<?> jsonArray2List(String jsonArrayStr, Class clazz) throws Exception{
		
		if (!CheckUtil.isNullString(jsonArrayStr)){
			JSONArray jsonArray = JSONArray.parseArray(jsonArrayStr);
			if (!jsonArray.isEmpty()) {
				Object[] obj = new Object[jsonArray.size()];
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					obj[i] = JSONObject.toJavaObject(jsonObject, clazz);;
				}
				List<?> list = Arrays.asList(obj);
				return list;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	

	/**
	 * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
	 * idValue, "name" : nameValue}, ...]
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static List<?> getDTOArray(String jsonString, Class clazz) {
		JSONArray jsonArray = JSONArray.parseArray(jsonString);
		Object[] obj = new Object[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			obj[i] = JSONObject.toJavaObject(jsonObject, clazz);;
		}
		return Arrays.asList(obj);
	}
	
	public static void main(String[] args) throws Exception{
		JSONArray jsonArray = new JSONArray();
		Zone zone1 = new Zone();
		zone1.setZoneNo("zone1");
		zone1.setZoneName("第一防区");
		zone1.setZoneDesc("前门防区");
		zone1.setZoneContactor("dongya");
		zone1.setZonePhone("123456");
		zone1.setZoneLoc("解放路123号");
		
		Zone zone2 = new Zone();
		zone2.setZoneNo("zone2");
		zone2.setZoneName("第二防区");
		zone2.setZoneDesc("后门防区");
		zone2.setZoneContactor("zhangsan");
		zone2.setZonePhone("789654");
		zone2.setZoneLoc("风光路123号");
		jsonArray.add(zone1);
		jsonArray.add(zone2);
		String jsonString = jsonArray.toJSONString();
		List<?> list = JsonUtil.jsonArray2List(jsonString, Class.forName("com.yunjing.entity.Zone"));
		for(Object obj:list){
			System.out.println(obj);
		}
	}
}
