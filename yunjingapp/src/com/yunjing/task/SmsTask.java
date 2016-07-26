package com.yunjing.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yunjing.model.Sms;
import com.yunjing.service.BusinessService;
import com.yunjing.service.QueryService;
import com.yunjing.util.Constants;
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
	@Scheduled(cron = "0/1 * *  * * ?")
	public void task(){
		List<Sms> toSendSmsList = queryService.getNotSendSms(service);
		if (null != toSendSmsList){
			
		}
		
	}

}
