package com.yunjing.util;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;
import com.yunjing.entity.Push;

public class XmPushUtil {
	public static final String packageName = "com.gato.security";
	public static final String appSecret = "d7c4X1oyAQpQjQx9EXNTGw==";
	public static boolean sendMessage(Push push){
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
			 sender.send(message, push.getXmAppId(), 0); //根据regID，发送消息到指定设备上，不重试。
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args){
		Push push = new Push();
		push.setMsgText("测试消息推送");
		push.setXmAppId("vAgYf9ErE4tpkkuX12JptIDmXmrwDJftsnelpLhhq/Q=");
		sendMessage(push);
		
	}

}
