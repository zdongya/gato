package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.Device;
import com.yunjing.entity.DeviceDto;

public interface DeviceDao {
	
	public abstract List<Device> getByDeviceName(String deviceName);
	
	public abstract Device getByDeviceNo(String deviceNo);
	
	public abstract void saveDevice(Device device);
	
	public abstract List<?> getAllPaging(DeviceDto device);

	public abstract List<?> getAll(DeviceDto device);
}
