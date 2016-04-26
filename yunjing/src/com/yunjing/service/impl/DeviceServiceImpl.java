package com.yunjing.service.impl;

import java.util.List;

import com.yunjing.dao.DeviceDao;
import com.yunjing.entity.Device;
import com.yunjing.entity.DeviceDto;
import com.yunjing.service.DeviceService;

public class DeviceServiceImpl implements DeviceService {
	private DeviceDao deviceDao;
	

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Override
	public List<?> getAllPaging(DeviceDto device) {
		return deviceDao.getAllPaging(device);
	}

	@Override
	public Device getByDeviceNo(String deviceNo) {
		return deviceDao.getByDeviceNo(deviceNo);
	}

	@Override
	public List<Device> getByDeviceName(String deviceName) {
		return deviceDao.getByDeviceName(deviceName);
	}

	@Override
	public List<?> getAll(DeviceDto device) {
		return deviceDao.getAll(device);
	}

}
