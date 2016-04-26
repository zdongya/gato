package com.yunjing.util;

import java.io.IOException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class Utils {
	public static final String KEY = "yunjing";
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

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
			return null;
		}
	}
	
	public static String getMD5_16Str(String source){
		return getMD5Str(source).substring(8, 24);
	}

	/**
	 * 输出json结果字符串
	 * 
	 * @param resp
	 * @param callResult
	 */
	public static void writerJsonResult(HttpServletResponse resp, CallResult callResult) {
		try {
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json; charset=utf-8");
			Writer writer = resp.getWriter();
			writer.write(JSON.toJSONString(callResult));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static String Bit32(String SourceString) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(SourceString.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String Bit16(String SourceString) {
		return Bit32(SourceString).substring(8, 24).toLowerCase();
	}

	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static String Md516(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(8, 24);//16位的加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	public static void main(String[] args) throws Exception{
		System.out.println(Bit16("测试abc"));
		
		System.out.println(Md516("测试abc"));
		System.out.println(getMD5Str("测试abc").substring(8, 24));
	}
}
