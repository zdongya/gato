package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.Device;
import com.yunjing.entity.DeviceDto;


public interface DeviceService {
	
	public abstract List<?> getAllPaging(DeviceDto device);
	
	public abstract Device getByDeviceNo(String deviceNo);
	
	public abstract List<Device> getByDeviceName(String username);

	public abstract List<?> getAll(DeviceDto device);

}
