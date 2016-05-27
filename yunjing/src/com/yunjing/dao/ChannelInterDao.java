package com.yunjing.dao;

import java.util.List;
import java.util.Map;

import com.yunjing.dto.DeviceDto;
import com.yunjing.dto.ZoneDto;
import com.yunjing.entity.Device;
import com.yunjing.entity.DevicePing;
import com.yunjing.entity.Push;
import com.yunjing.entity.WarningInfo;
import com.yunjing.entity.Zone;

/**
 * 双方通道接口
 * @author DONGYA
 *
 */
public interface ChannelInterDao {
	
	public void saveDevice(DeviceDto device);
	//更新设备信息
	public void updateDevice(DeviceDto deviceDto);
	
	public Integer getCountByDeviceNo(String deviceNo);
	public int getCountByZoneNo(String zoneNo);
	public int getCountByWaringId(String warningId);
	public void saveWarningInfo(WarningInfo info);
	public String getZoneNameByZoneNo(String zoneNo);
	
	/**
	 * 获取防区关联的所有用户
	 * @param zoneNo
	 * @return
	 */
	public List<String> getZoneUsers(String zoneNo);
	
	public void savePushMsg(Push push);
	
	//更新防区信息
	public void saveZone(ZoneDto zone);
	public void updateZone(ZoneDto zone);
	public void updateZoneState(ZoneDto zone);
	public void deleteZone(String zoneNo);
	public void cleanWarningInfo(String zoneNo);
	public void cleanLog(String zoneNo);
	
	/**
	 * 查询初始化防区时不存在减少的防区
	 * @param deviceNo
	 * @return
	 */
	public List<String> getInitNotExsitZones(Map<String, Object> parmMap);
	public void saveOrUploadDevicePing(DevicePing devicePing);
	public DeviceDto getDeviceByNo(String deviceNo);
	public List<String> getDeviceZones(String deviceNo);
}
