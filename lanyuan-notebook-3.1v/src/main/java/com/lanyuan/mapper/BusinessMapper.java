package com.lanyuan.mapper;

import java.util.List;

import com.lanyuan.entity.DeviceFormMap;
import com.lanyuan.entity.SysUserFormMap;
import com.lanyuan.mapper.base.BaseMapper;
/**
 * 业务系统mapper
 * @author DONGYA
 *
 */
public interface BusinessMapper extends BaseMapper{
	
	public List<BusinessMapper> findSysUserPage(SysUserFormMap sysUserFormMap);
	
	public List<BusinessMapper> findDevicePage(DeviceFormMap deviceFormMap);

	/**
	 * 查询设备绑定用户
	 * @param sysUserFormMap
	 * @return
	 */
	public List<SysUserFormMap> findDeviceUsers(SysUserFormMap sysUserFormMap);

}
