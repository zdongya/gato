package com.yunjing.util;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import com.yunjing.entity.Push;

public class XmPushUtil {
	private static final String packageName = "com.gato.security";
	private static final String appSecret = "VCeEWiCc1z2WnzcDJfEYWQ==";
	private static final String appSecret_ios = "ujwOLL9mRr1C/JgBC2WsKg==";
	private static final boolean ios_env = false; //是否是正式环境

	public static boolean sendMessage(Push push, int appType){
		if (appType == 0){
			return sendMessageAndroid(push);
		} else {
			return sendMessageIos(push);
		}
	}
	private static boolean sendMessageIos(Push push) {
		
		try {
			if (ios_env){ //正式环境
				Constants.useOfficial();
			} else {
				Constants.useSandbox();
			}
			String description = "收到防区报警信息";
		     Message message = new Message.IOSBuilder()
		             .description(description)
		             .soundURL("default")    // 消息铃声
		             .badge(1)               // 数字角标
		             .extra(Constants.EXTRA_PARAM_NOTIFY_FOREGROUND, "0")  // 自定义键值对
		             .build();
			 
			 Sender sender = new Sender(appSecret_ios);
			 Result result = sender.send(message, push.getXmAppId(), 0); //根据regID，发送消息到指定设备上，不重试。
			 System.out.println("ios push Server response【MessageId: " + result.getMessageId() + ";ErrorCode:" + result.getErrorCode().getDescription() + ";Reason:" + result.getReason());
			 if (result.getErrorCode().equals(ErrorCode.Success)){
				 return true;
			 }
			 return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	private static boolean sendMessageAndroid(Push push){
		 try {
			Constants.useOfficial();
			 Sender sender = new Sender(appSecret);
			 String title = "广拓云周界";
			 String description = "收到防区报警信息";
			 Message message = new Message.Builder()
			            .title(title)
			            .description(description).payload(push.getMsgText())
			            .restrictedPackageName(packageName)
			            .extra(Constants.EXTRA_PARAM_NOTIFY_FOREGROUND, "0")
			            .notifyType(1)     // 使用默认提示音提示
			            .build();
			 Result result = sender.send(message, push.getXmAppId(), 0); //根据regID，发送消息到指定设备上，不重试。
			 System.out.println("andorid push Server response【MessageId: " + result.getMessageId() + ";ErrorCode:" + result.getErrorCode().getDescription() + ";Reason:" + result.getReason());
			 if (result.getErrorCode().equals(ErrorCode.Success)){
				 return true;
			 }
			 return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception{
		Push push = new Push();
		push.setMsgText("测试消息推送bbb");
		push.setXmAppId("98JlTpmzXNvRuLViuhDRsM9PfjzwxUV8teuWfC9D3HQ=");
		sendMessage(push, 1);
//		Constants.useSandbox();
//		 String description = "测试推送66";
//	     Message message = new Message.IOSBuilder()
//	             .description(description)
//	             .soundURL("default")    // 消息铃声
//	             .badge(1)               // 数字角标
//	             .category("action")     // 快速回复类别
//	             .extra("key", "value")  // 自定义键值对
//	             .build();
//	     Sender sender = new Sender(appSecret_ios);
//	     Result result = sender.send(message, "98JlTpmzXNvRuLViuhDRsM9PfjzwxUV8teuWfC9D3HQ=", 0); //根据regID，发送消息到指定设备上，不重试。
//	     System.out.println("Server response【MessageId: " + result.getMessageId() + ";ErrorCode:" + result.getErrorCode().getDescription() + ";Reason:" + result.getReason());
		
	}

}
