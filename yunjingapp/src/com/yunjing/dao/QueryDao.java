package com.yunjing.dao;

import java.util.List;
import java.util.Map;

import com.yunjing.model.Collect;
import com.yunjing.model.Device;
import com.yunjing.model.DeviceGroup;
import com.yunjing.model.Sms;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;

/**
 * 查询dao
 * @author DONGYA
 *
 */
public interface QueryDao {
	
	public Device queryDeviceById(String deviceNo);
	
	public Zone queryZoneById(String zoneNo);

	public List<?> queryUserDevices(String userId);
	
	
	public int queryDeviceGroupCountByName(String groupName);
	
	
	/**
	 * 查询用户的分组
	 * @param userId
	 * @return
	 */
	public List<DeviceGroup> queryDeviceGroups(String userId);
	
	
	public List<WarningInfo> queryWarning(int istate, String userId);
	
	public List<WarningInfo> queryWarning(String userId);
	
	public WarningInfo getWarningsById(String warningId);
	
	public List<Collect> getUserCollects(String userId);


	public int countCollectByUserAndDevice(String userId, String deviceNo);

	public boolean queryUserBindDeviceFlag(String deviceNo, String userId);
	
	/**
	 * 绑定设备检查,通过true，不能绑定false
	 * @param device
	 * @return
	 */
	public boolean bindDeviceCheck(Device device);
	
	public boolean checkDeviceByNo(String deviceNo);


	public int getCollectCountById(String collectId, String userId, String deviceNo);

	public List<?> queryZonesByDeviceNo(String userId, String deviceNo);

	public int checkOwnZone(Zone zone);
	
	public int checkZoneStateChange(String zoneNo,String istate);
	
	public int checkWarningStateChange(String warningId, String istate);
	
	public boolean checkIsBindDevice(String userId, String deviceNo);
	
	public boolean haveBindDataCheck(String userId, String deviceNo);

	/**
	 * 获取首页数据
	 * @param userId
	 * @return
	 */
	public Map<String, String> queryIndexData(String userId);

	public List<Sms> getNotSendSms(String service);

	public List<Sms> getSmsReportList(String service);

	/**
	 * 校验是否属于自己管理的防区
	 * @param userId:用户编号
	 * @param deviceNo:设备编号
	 * @return
	 */
	public boolean checkOwnManageDevice(String userId, String deviceNo);
	
	
	/**
	 * 一键布撤防时校验用户密码
	 * @param deviceNo
	 * @param pwd
	 * @return
	 */
	public boolean checkDeviceDefencePwd(String deviceNo, String pwd);

	public List<?> queryCheckPwdDevices(String userId);

	public int countPushConfig(String userId);

	public Map<String, Object> queryPushConfig(String userId);
	
}
