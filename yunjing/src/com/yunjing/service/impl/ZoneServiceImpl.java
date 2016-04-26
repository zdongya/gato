package com.yunjing.service.impl;

import java.util.List;

import com.yunjing.dao.ZoneDao;
import com.yunjing.entity.Zone;
import com.yunjing.service.ZoneService;

public class ZoneServiceImpl implements ZoneService {
	private ZoneDao ZoneDao;
	

	public void setZoneDao(ZoneDao ZoneDao) {
		this.ZoneDao = ZoneDao;
	}

	@Override
	public List<?> getAllPaging(Zone zone) {
		return ZoneDao.getAllPaging(zone);
	}

	@Override
	public Zone getByZoneNo(String ZoneNo) {
		return ZoneDao.getByZoneNo(ZoneNo);
	}

	@Override
	public List<Zone> getByZoneName(String ZoneName) {
		return ZoneDao.getByZoneName(ZoneName);
	}

	@Override
	public List<Zone> getZonesByDevice(String deviceNo) {
		return ZoneDao.getZonesByDeviceNo(deviceNo);
	}

}
