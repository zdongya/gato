package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.Zone;


public interface ZoneService {
	
	public abstract List<?> getAllPaging(Zone zone);
	
	public abstract Zone getByZoneNo(String ZoneNo);
	
	public abstract List<Zone> getByZoneName(String ZoneName);

	public abstract List<Zone> getZonesByDevice(String deviceNo);

}
