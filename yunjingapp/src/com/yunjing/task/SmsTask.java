package com.yunjing.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yunjing.model.Sms;
import com.yunjing.service.BusinessService;
import com.yunjing.service.QueryService;
import com.yunjing.util.Constants;
import com.yunjing.util.SmsSender;
import com.yunjing.util.YunxinSmsUtil;
/**
 * 云信短信线程
 * @author DONGYA
 */
@Component
public class SmsTask {
	
	@Autowired
	private QueryService queryService;
	@Autowired
	private BusinessService businessService;
	
	public static final String service = Constants.getSmsService();
	
	/**
	 * 每秒执行一次
	 */
	@Scheduled(cron = "0/5 * *  * * ?")
	public void task(){
		try {
//			updateOverTimeSms();
//			sendMsg();
//			getReport();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("短信发送异常。。。");
		}
	}

	private void updateOverTimeSms() {
		businessService.updateOverTimeSms();
		
	}

	private void getReport() {
		List<Sms> list = queryService.getSmsReportList(service);
		if (null != list && list.size()>0){
			System.out.println("待获取发送报告短信条数：" + list.size());
			Sms sms = list.get(0);
			List<SmsSender> results = YunxinSmsUtil.getReport(sms.getApplyId());
			for (SmsSender sender:results){
				businessService.updateSms(sender);
			}
		}
	}

	private void sendMsg() {
		List<Sms> toSendSmsList = queryService.getNotSendSms(service);
		if (null != toSendSmsList){
			System.out.println("待发送短信条数：" + toSendSmsList.size());
			for (Sms message:toSendSmsList){
				SmsSender result = YunxinSmsUtil.sendSms(message.getMobileNo(), message.getSmsContent());
				result.setSmsId(message.getId());
				businessService.updateSms(result);
			}
		}
	}

}
