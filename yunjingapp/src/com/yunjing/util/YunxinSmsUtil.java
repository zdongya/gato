package com.yunjing.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 云信短信工具类
 * 
 * @author DONGYA
 *
 */
public class YunxinSmsUtil {

	private static final String USERCODE = "SHGTCF";
	private static final String USERPASS = "SHGTabc155";
	private static Map<String, String> errMap = new HashMap<String, String>();
	static {
		errMap.put("-1", "提交接口错误");
		errMap.put("-3", "用户名或密码错误");
		errMap.put("-4", "短信内容和备案的模板不一样");
		errMap.put("-5", "签名不正确");
		errMap.put("-7", "余额不足");
		errMap.put("-8", "通道错误");
		errMap.put("-9", "无效号码");
		errMap.put("-10", "签名内容不符合长度");
		errMap.put("-11", "用户有效期过");
		errMap.put("-12", "黑名单");
		errMap.put("-13", "语音验证码的Amount参数必须是整形字符串");
		errMap.put("-14", "语音验证码的内容只能为数字");
		errMap.put("-15	", "语音验证码的内容最长为6位");
		errMap.put("-16	", "余额请求过于频繁，5秒才能取余额一次");
		errMap.put("-17	", "非法IP");
	}

	public static SmsSender sendSms(String mobileNo, String content) {
		SmsSender result = new SmsSender();
		result.setItype(0);
		result.setMobileNo(mobileNo);
		boolean flag = false;
		try {
			if (Utils.isMobilephone(mobileNo)) {
				Map<String, String> paramMap = getBaseMap();
				paramMap.put("DesNo", mobileNo);
				paramMap.put("Msg", content);
				paramMap.put("Channel", "0");
				String responseXml = HttpClientUtil.callPost("http://120.55.197.77:1210/Services/MsgSend.asmx/SendMsg",
						paramMap);
				System.out.println("发送短信返回：" + responseXml);
				if (null != responseXml) {
					SAXReader sax = new SAXReader();
					Document doc = sax.read(new ByteArrayInputStream(responseXml.getBytes("UTF-8")));
					Element ele = doc.getRootElement();
					String responseValue = ele.getText();
					if (responseValue.length() < 5) { // 发送失败
						String errDesc = errMap.get(responseValue);
						if (null == errDesc) {
							errDesc = responseValue;
						}
						result.setErrDesc(errDesc);
					} else {
						flag = true;
						result.setBatchId(responseValue);
						result.setErrDesc("提交成功");
						System.out.println("短信发送成功，手机号:" + mobileNo + ";返回批次号:" + responseValue);
					}
				} else {
					result.setErrDesc("发送失败，接口调用失败");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送短信程序异常。。。。");
			result.setErrDesc("发送短信失败，程序异常");
		}
		result.setResult(flag);
		result.setDbFlag(flag == true ? 1 : 3);
		return result;
	}

	public static List<SmsSender> getReport(String batchid) {
		List<SmsSender> list = new ArrayList<SmsSender>();
		try {
			Map<String, String> paramMap = getBaseMap();
			paramMap.put("batchNumber", batchid);
			String responseXml = HttpClientUtil.callPost("http://120.55.197.77:1210/Services/MsgSend.asmx/GetReport", paramMap);
			System.out.println("获取状态报告返回：" + responseXml);
			if (null != responseXml) {
				SAXReader sax = new SAXReader();
				Document doc = sax.read(new ByteArrayInputStream(responseXml.getBytes("UTF-8")));
				Element ele = doc.getRootElement();
				String responseValue = ele.getText();
				SmsSender info;
				for (String str : responseValue.split("\\|")) {
					if (!CheckUtil.isNullString(str)) {
						info = new SmsSender();
						info.setItype(1);
						String cbatchid = str.split(",")[0];
						String cmobile = str.split(",")[1];
						String errDesc = str.split(",")[2];
						boolean flag = false;
						if (errDesc.equals("DELIVRD")) {
							flag = true;
							errDesc = "发送成功";
						}
						info.setBatchId(cbatchid);
						info.setMobileNo(cmobile);
						info.setResult(flag);
						info.setDbFlag(flag == true ? 2 : 3);
						info.setErrDesc(errDesc);
						list.add(info);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送短信程序异常。。。。");
		}
		return list;
	}

	private static Map<String, String> getBaseMap() {
		Map<String, String> baseMap = new HashMap<String, String>();
		baseMap.put("userCode", USERCODE);
		baseMap.put("userPass", USERPASS);
		return baseMap;
	}

	public static void main(String[] args) {
		// sendSms("18601760246", "验证码222【上海广拓】");
		// sendSms("18601760246", "验证码111");
		getReport("2314701291880142275");
	}

}
