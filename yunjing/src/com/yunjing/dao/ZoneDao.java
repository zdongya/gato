package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.Zone;

public interface ZoneDao {
	
	public abstract List<Zone> getByZoneName(String zoneName);
	
	public abstract Zone getByZoneNo(String zoneNo);
	
	public abstract void saveZone(Zone zone);
	
	public abstract List<?> getAllPaging(Zone zone);

	public abstract List<Zone> getZonesByDeviceNo(String deviceNo);
}
