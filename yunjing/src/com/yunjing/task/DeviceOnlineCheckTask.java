package com.yunjing.task;

import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yunjing.service.DevicePingService;

public class DeviceOnlineCheckTask extends TimerTask{
	private DevicePingService pingService = new DevicePingService();
	private static Log log =  LogFactory.getLog("onlineCheckTask");

	@Override
	public void run() {
		log.info("设备心跳状态检查中。。。。");
		checkDevice();
	}

	private void checkDevice() {
		List<String> devices = pingService.getOutTimePing();
		if(null != devices && devices.size() > 0){
			log.info("待下线设备个数---：" + devices.size());
			pingService.deviceOffLine(devices);
		}
		
	}

}
