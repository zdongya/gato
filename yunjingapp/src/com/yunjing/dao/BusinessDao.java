package com.yunjing.dao;

import com.yunjing.model.Collect;
import com.yunjing.model.DeviceGroup;
import com.yunjing.model.OperatorLog;
import com.yunjing.model.Push;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.util.SmsSender;

public interface BusinessDao {
	public void saveDeviceGroup(DeviceGroup deviceGroup);
	
	/**
	 * 处理报警信息
	 * @param warningInfo
	 */
	public void hanleWarnings(WarningInfo warningInfo);

	public void saveCollect(Collect collect);
	
	
	/**
	 * 绑定设备
	 * @param userId
	 * @param deviceNo
	 */
	public void saveUserDevice(String userId, String deviceNo, int itype,String pwd);
	
	public void deleteCollect(String collectId);

	public void updateZone(Zone zone);

	public void saveRetroaction(String userId, String contents, String contact);
	
	/**
	 * 更新防区布撤防状态
	 * @param zoneNo
	 * @param istate
	 */
	public void changeZoneState(String zoneNo, String istate);
	
	

	/**删除无效的未推送消息
	 * @param topic:主题
	 * @param zoneNo:防区编号
	 * @param itype:消息类型
	 */
	public void deleteNotPushInvalidMsg(String topic, String zoneNo, String itype);
	
	/**
	 * 保存推送消息
	 * @param push
	 */
	public void savePushMsg(Push push);
	
	public void unbindDevice(String userId, String deviceNo);

	public void editDeviceName(String deviceNo, String deviceName);

	public void bindDevice(String userId, String deviceNo, int itype, String pwd);
	
	public void addOperatorLog(OperatorLog log);

	public void updateSmsCommitResult(SmsSender sender);

	public void updateSmsSendResult(SmsSender sender);

	public void updateNotSendSmsToFail();

	public void updateNotGetReportToSuccess();

	/**
	 * 删除无效的一键布撤防、一键消警消息
	 * @param topic
	 * @param itype
	 */
	public void deleteNotPushInvalidMsg(String topic, String itype);

	
}
