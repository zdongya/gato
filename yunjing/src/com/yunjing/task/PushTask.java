package com.yunjing.task;

import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yunjing.entity.Push;
import com.yunjing.service.PushService;
import com.yunjing.util.CallResult;
import com.yunjing.util.MqttMsgSendUtil;
import com.yunjing.util.XmPushUtil;

/**
 * 报警信息处理结果推送task
 * @author DONGYA
 *
 */
public class PushTask extends TimerTask{
	private PushService pushService = new PushService();
	private static Log log =  LogFactory.getLog("PushTask");
	
	@Override
	public void run() {
		pushMqttMsg();
		pushXmMsg();
		
	}

	private void pushMqttMsg() {
		List<Push> msgs = pushService.getToPushMsgs(0);
		if (null != msgs && msgs.size()>0){
			log.info("【MQTT推送】待推送消息条数:" + msgs.size());
			for (Push pushMsg:msgs){
				boolean flag = MqttMsgSendUtil.sendMessage(pushMsg.getTopic(), pushMsg.getMsgText());
				String istate = "-1";
				if (flag){ //推送成功
					istate = "1";
					log.info("【MQTT推送】推送消息成功。。。，msgId:" + pushMsg.getMsgId());
				} else {
					log.error("【MQTT推送】推送消息失败。。。，msgId:" + pushMsg.getMsgId());
				}
				CallResult result = pushService.updatePushResult(pushMsg.getMsgId(), istate);
				if (result.getCode().equals("0000")){
					log.info("【MQTT推送】更新推送结果成功");
				} else {
					log.error("【MQTT推送】更新推送结果失败");
				}
			}
			
		}
	}
	
	/**
	 * 小米推送
	 */
	private void pushXmMsg() {
		List<Push> msgs = pushService.getToPushMsgs(2);
		if (null != msgs && msgs.size()>0){
			log.info("【小米推送】待推送消息条数:" + msgs.size());
			for (Push pushMsg:msgs){
				String istate = "-1";
				boolean flag = false;
				if(null != pushMsg.getXmAppId() && pushMsg.getXmAppId().length() > 0){
					flag = XmPushUtil.sendMessage(pushMsg, pushMsg.getAppType());
				}
				
				if (flag){ //推送成功
					istate = "1";
					log.info("【小米推送】推送消息成功。。。，msgId:" + pushMsg.getMsgId());
				} else {
					log.error("【小米推送】推送消息失败。。。，msgId:" + pushMsg.getMsgId());
				}
				CallResult result = pushService.updatePushResult(pushMsg.getMsgId(), istate);
				if (result.getCode().equals("0000")){
					log.info("【小米推送】更新推送结果成功");
				} else {
					log.error("【小米推送】更新推送结果失败");
				}
			}
			
		}
	}

}
