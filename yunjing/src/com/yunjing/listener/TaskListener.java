package com.yunjing.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yunjing.task.PushTask;

public class TaskListener implements ServletContextListener {
	private Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("容器初始化成功，开始启动推送任务");
		timer = new Timer(true);
		timer.schedule(new PushTask(), 10*1000, 1000);

	}

}
