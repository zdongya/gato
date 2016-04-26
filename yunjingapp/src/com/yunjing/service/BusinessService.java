package com.yunjing.service;

import com.yunjing.model.Device;
import com.yunjing.model.OperatorLog;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.util.CallResult;

/**
 * 业务操作service
 * @author DONGYA
 */
public interface BusinessService {
	
	/**
	 * 报警信息处理
	 * @param warningInfo
	 * @return
	 */
	public CallResult handleWarnings(WarningInfo warningInfo);

	public CallResult addCollects(String userId, String deviceNo);

	public CallResult bindDevice(Device device);

	public CallResult cancelCollects(String collectId, String userId, String deviceNo);

	public CallResult editZone(Zone zone);

	public CallResult addRetroaction(String userId, String contents, String contact);

	public CallResult defence(String userId, String zoneNo, String istate, String ipAddr);

	/**
	 * 解绑设备
	 * @param userId
	 * @param deviceNo
	 * @return
	 */
	public CallResult unbindDevice(String userId, String deviceNo);

	public CallResult editDeviceName(String userId, String deviceNo, String deviceName);

	public void saveOperatorLog(OperatorLog log);
	
	
}
