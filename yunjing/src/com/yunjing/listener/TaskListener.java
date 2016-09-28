package com.yunjing.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yunjing.service.DevicePingService;
import com.yunjing.task.DeviceOnlineCheckTask;
import com.yunjing.task.PushTask;
import com.yunjing.util.CallResult;

public class TaskListener implements ServletContextListener {
	private Timer timer = null;
	private Timer pingTimer = null;

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("容器初始化成功");
		DevicePingService pingService = new DevicePingService();
		System.out.println("开始初始化设备心跳时间");
		CallResult result = pingService.initPingDate();
		if (result.getCode().equals("0000")){
			System.out.println("初始化设备心跳时间成功");
		}
		
		timer = new Timer(true);
		timer.schedule(new PushTask(), 10*1000, 1000);
		
		pingTimer = new Timer(true);
		pingTimer.schedule(new DeviceOnlineCheckTask(), 10*1000, 1000*60);
		
	}

}
