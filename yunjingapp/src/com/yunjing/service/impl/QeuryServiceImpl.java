package com.yunjing.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunjing.dao.QueryDao;
import com.yunjing.model.Device;
import com.yunjing.model.Zone;
import com.yunjing.service.PageService;
import com.yunjing.service.QueryService;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.Pagination;

@Service(value="queryService")
public class QeuryServiceImpl implements QueryService{
	
	@Autowired
	private QueryDao queryDao;
	
	@Autowired
	private PageService pageService;
	@Override
	public Device queryDeviceById(String deviceNo) {
		return queryDao.queryDeviceById(deviceNo);
	}
	
	@Override
	public Zone queryZoneById(String zoneNo) {
		return queryDao.queryZoneById(zoneNo);
	}
	
	@Override
	public List<?> queryUserDevices(String userId) {
		return queryDao.queryUserDevices(userId);
	}

	@Override
	public List<?> queryWarningInfo(Integer istate,String userId) {
		if (null == istate){
			return queryDao.queryWarning(userId);
		} else {
			return queryDao.queryWarning(istate, userId);
		}
	}

	@Override
	public List<?> queryUserCollects(String userId) {
		return queryDao.getUserCollects(userId);
	}

	@Override
	public Pagination queryWarningInfo(String userId, Integer istate, int pn) {
		if (pn < 0){
			pn = 1;
		}
		String queryString = "select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid='" + userId + "' and istate=1) order by  t.warndate desc";
		if (null != istate){
			queryString = "select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where t.istate=" + istate.intValue() + " and z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid='" + userId + "' and istate=1) order by t.istate asc, t.warndate desc";
		}
		return pageService.queryForPage(queryString, pn);
	}

	@Override
	public boolean userBindDeviceFlag(String deviceNo, String userId) {
		return queryDao.queryUserBindDeviceFlag(deviceNo, userId);
	}

	@Override
	public List<?> queryDeviceZones(String deviceNo) {
		return queryDao.queryZonesByDeviceNo(deviceNo);
	}

	@Override
	public Pagination queryZones(String userId, String deviceName, String zoneName, int pn) {
		if (pn <= 0){
			pn = 1;
		}
		StringBuilder builder = new StringBuilder("SELECT t.*,d.devicename FROM tb_zone t,tb_device d WHERE d.deviceno=t.deviceno AND d.deviceno IN (SELECT deviceno FROM tb_user_device_map WHERE cuserid='" + userId + "') ");
		if (!CheckUtil.isNullString(deviceName)){
			builder.append(" and d.devicename='" + deviceName + "'");
		}
		if (!CheckUtil.isNullString(zoneName)){
			builder.append(" and t.zonename='" + zoneName + "'");
		}
		
		builder.append(" order by t.adddate desc");
		return pageService.queryForPage(builder.toString(), pn);
	}

	@Override
	public Map<String, String> queryIndexData(String userId) {
		return queryDao.queryIndexData(userId);
	}

}
