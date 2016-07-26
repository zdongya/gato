package com.yunjing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientUtil {

	/**
	 * 使用GetMethod
	 * 
	 * @param url
	 * @return
	 */
	public static String callGet(String url) {
		GetMethod getMethod = null;
		try {
			System.out.println("httpclient get 访问【" + url + "】中。。。");
			HttpClient client = new HttpClient();
			getMethod = new GetMethod(url);
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == 200) {
				String response = getMethod.getResponseBodyAsString();
				return response;
			} else {
				System.out.println("httpclient 访问【" + url + "】失败，statusCode:" + statusCode);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != getMethod) {
				getMethod.releaseConnection();
			}
		}
	}

	public static String callPost(String url, Map<String, String> parmp) {
		PostMethod post = null;
		try {
			System.out.println("httpclient post 访问【" + url + "】中。。。");
			HttpClient client = new HttpClient();
			post = new PostMethod(url);
			List<NameValuePair> list = getParamsList(parmp);
			if (list != null) {
				NameValuePair[] pairs = (NameValuePair[]) list.toArray(new NameValuePair[list.size()]);
				post.setRequestBody(pairs);
			}
			int statusCode = client.executeMethod(post);
			if (statusCode == 200) {
				String response = post.getResponseBodyAsString();
				return response;
			} else {
				System.out.println("httpclient post 访问【" + url + "】失败，statusCode:" + statusCode);
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != post) {
				post.releaseConnection();
			}
		}

	}

	private static List<NameValuePair> getParamsList(Map paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Set<Map.Entry> set = paramsMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + ":" + value);
			list.add(new NameValuePair(key, value));
		}
		return list;
	}

	public static void main(String[] args) {
//		String url = "http://120.55.197.77:1210/Services/MsgSend.asmx/GetBalance?userCode=SHGTCF&userPass=SHGTabc155";
//		String response = callGet(url);
		String url = "http://120.55.197.77:1210/Services/MsgSend.asmx/GetBalance";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userCode", "SHGTCF");
		map.put("userPass", "SHGTabc155");
		String response = callPost(url, map);
		System.out.println(response);
	}
}
