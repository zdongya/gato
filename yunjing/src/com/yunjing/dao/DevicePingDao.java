package com.yunjing.dao;

import java.util.List;

public interface DevicePingDao {
	/**
	 * 容积启动时初始化心跳时间
	 */
	public void initPingDate();
	
	/**
	 * 获取心跳超时的设备编号
	 * @return
	 */
	public List<String> getOutTimePing();
	
	/**
	 * 设备下线
	 * @param deviceNo
	 */
	public void deviceOffLine(String deviceNo);
	
	/**
	 * 防区下线
	 * @param deviceNo
	 */
	public void zoneOffLine(String deviceNo);
}
