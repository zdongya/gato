package com.yunjing.dao;

import java.util.List;

import com.yunjing.model.Collect;
import com.yunjing.model.Device;
import com.yunjing.model.DeviceGroup;
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

	public List<?> queryZonesByDeviceNo(String deviceNo);

	public int checkOwnZone(Zone zone);
	
	public int checkZoneStateChange(String zoneNo,String istate);
	
	public int checkWarningStateChange(String warningId, String istate);
	
	public boolean checkIsBindDevice(String userId, String deviceNo);
	
	public boolean haveBindDataCheck(String userId, String deviceNo);
	
}
