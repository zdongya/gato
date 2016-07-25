package com.yunjing.service;

import java.util.List;
import java.util.Map;

import com.yunjing.model.Device;
import com.yunjing.model.Zone;
import com.yunjing.util.Pagination;
/**
 * 查询service
 * @author DONGYA
 *
 */
public interface QueryService {
	
	/**
	 * 根据设备编号查询设备信息
	 * @param deviceNo
	 * @return
	 */
	public Device queryDeviceById(String deviceNo);

	
	/**
	 * 根据防区编号查询设备信息
	 * @param zoneNo
	 * @return
	 */
	public Zone queryZoneById(String zoneNo);
	
	
	/**
	 * 查询用户设备
	 * @param userId
	 * @return
	 */
	public List<?> queryUserDevices(String userId);
	
	public List<?> queryWarningInfo(Integer istate, String userId);
	
	/**
	 * 查询用户收藏
	 * @param userId
	 * @return
	 */
	public List<?> queryUserCollects(String userId);


	public Pagination queryWarningInfo(String userId, Integer istate, int pn);
	
	/**
	 * 用户是否已经绑定了某设备
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	public boolean userBindDeviceFlag(String deviceNo, String userId);


	/**
	 * 查询设备的防区列表
	 * @param deviceNo
	 * @return
	 */
	public List<?> queryDeviceZones(String deviceNo);


	public Pagination queryZones(String userId, String deviceName, String zoneName, int pn);


	/**
	 * 获取首页展示的banner数据
	 * @param userId
	 * @return
	 */
	public Map<String, String> queryIndexData(String userId);

}
