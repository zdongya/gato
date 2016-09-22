package com.yunjing.service;

import com.yunjing.model.Device;
import com.yunjing.model.OperatorLog;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.util.CallResult;
import com.yunjing.util.SmsSender;

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
	
	/**
	 * 更新短信发送结果
	 * @param result
	 */
	public void updateSms(SmsSender sender);

	/**
	 * 更新过期短信
	 */
	public void updateOverTimeSms();

	/**
	 * 设备一键布撤防
	 * @param userId
	 * @param pwd
	 * @param deviceNo
	 * @param istate
	 * @param ipAddr
	 * @return
	 */
	public CallResult deviceDefence(String userId, String pwd, String deviceNo, String istate, String ipAddr);
	

	//对用户所属防区全部消警
	public CallResult deviceHandleWaring(String userId, String ipAddr);

	/**
	 * 批量布撤防
	 * @param userId
	 * @param pwd
	 * @param deviceNo
	 * @param zoneNos
	 * @param istate
	 * @param ipAddr
	 * @return
	 */
	public CallResult defenceZones(String userId, String pwd,String deviceNo, String[] zoneNos, String istate, String ipAddr);

	public CallResult editZoneStrainVpt(String userId, String zoneNo, String zoneStrainVpt, String ipAddr);

	public CallResult pushConfig(String userId, int itype);
	
	//消除用户某防区的报警
	public CallResult deviceHandleWaring(String userId, String deviceNo, String ipAddr);

	public CallResult editZoneParam(String userId, String zoneNo, String zoneParam, String ipAddr);
	
}
