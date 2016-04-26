package com.yunjing.dao.impl;

import java.util.List;

import com.yunjing.dao.BaseDao;
import com.yunjing.dao.ZoneDao;
import com.yunjing.entity.Zone;
import com.yunjing.util.CheckUtil;

public class ZoneDaoImpl extends BaseDao implements ZoneDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Zone> getByZoneName(String zoneName) {
		String sql = "from Zone as d where d.zoneName=?";
		return super.find(sql, zoneName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Zone getByZoneNo(String zoneNo) {
		String hql = "from Zone as d where d.zoneNo=?";
		List<Zone> zoneList = super.find(hql, zoneNo);
		return zoneList.isEmpty()?null:zoneList.get(0);
	}

	@Override
	public void saveZone(Zone zone) {
		super.saveOrUpdate(zone);
		
	}
	
	
	@Override
	public List<?> getAllPaging(Zone zone) {
		StringBuilder builder = new StringBuilder("from Zone as model where 1=1 ");
		if (null == zone){
			zone = new Zone();
		}
		if (!CheckUtil.isNullString(zone.getZoneNo())){
			builder.append(" and model.zoneNo='").append(zone.getZoneNo()).append("'");
		}
		if (!CheckUtil.isNullString(zone.getZoneName())){
			builder.append(" and model.zoneName='").append(zone.getZoneName()).append("'");
		}
		System.out.println("queryString:" + builder.toString());
		return super.getAllPaging(builder.toString());
	}

	@Override
	public List<Zone> getZonesByDeviceNo(String deviceNo) {
		String hql = "from Zone as model where model.device.deviceNo=?";
		@SuppressWarnings("unchecked")
		List<Zone> zoneList = super.find(hql, deviceNo);
		return zoneList;
	}

}
