package com.yunjing.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunjing.dao.QueryDao;
import com.yunjing.dto.WarnSearch;
import com.yunjing.model.Device;
import com.yunjing.model.Sms;
import com.yunjing.model.Zone;
import com.yunjing.service.PageService;
import com.yunjing.service.QueryService;
import com.yunjing.util.BannerImgDb;
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
		String queryString = "select t.*,z.zonename,d.devicename,d.online,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid='" + userId + "' and istate=1 and CHECKPWDFLAG=0) order by  t.warndate desc";
		if (null != istate){
			queryString = "select t.*,z.zonename,d.devicename,d.online,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where t.istate=" + istate.intValue() + " and z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid='" + userId + "' and istate=1 and CHECKPWDFLAG=0) order by t.istate asc, t.warndate desc";
		}
		return pageService.queryForPage(queryString, pn);
	}

	@Override
	public boolean userBindDeviceFlag(String deviceNo, String userId) {
		return queryDao.queryUserBindDeviceFlag(deviceNo, userId);
	}

	@Override
	public List<?> queryDeviceZones(String userId, String deviceNo) {
		return queryDao.queryZonesByDeviceNo(userId, deviceNo);
	}

	@Override
	public Pagination queryZones(String userId, String deviceName, String zoneName, int pn) {
		if (pn <= 0){
			pn = 1;
		}
		//修改过密码未重新绑定的设备防区不显示
		StringBuilder builder = new StringBuilder("SELECT t.*,(SELECT itype FROM tb_user_device_map where cuserid='" + userId + "' AND deviceno=t.DEVICENO) AS useType,"
				+ "d.devicename FROM tb_zone t,tb_device d WHERE d.deviceno=t.deviceno and d.online=1 AND d.deviceno IN (SELECT deviceno FROM tb_user_device_map WHERE CHECKPWDFLAG=0 AND cuserid='" + userId + "') ");
		if (!CheckUtil.isNullString(deviceName)){
			builder.append(" and d.devicename like '%" + deviceName + "%'");
		}
		if (!CheckUtil.isNullString(zoneName)){
			builder.append(" and t.zonename like '%" + zoneName + "%'");
		}
		
		builder.append(" order by t.adddate desc");
		return pageService.queryForPage(builder.toString(), pn);
	}

	@Override
	public Map<String, String> queryIndexData(String userId) {
		return queryDao.queryIndexData(userId);
	}

	@Override
	public List<Sms> getNotSendSms(String service) {
		return queryDao.getNotSendSms(service);
	}

	@Override
	public List<Sms> getSmsReportList(String service) {
		return queryDao.getSmsReportList(service);
	}

	@Override
	public Pagination queryWarns(WarnSearch warnSearch, int pn) {
		if (pn < 0){
			pn = 1;
		}
		StringBuilder builder = new StringBuilder("select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid='" + warnSearch.getUserId() + "') ");
		
		if (warnSearch.getSearchType() == 0){ //按照时间段搜索
			if (!CheckUtil.isNullString(warnSearch.getBeginDate())){
				String beginDate = warnSearch.getBeginDate() + " 00:00:00";
				builder.append(" and t.warnDate>= '").append(beginDate).append("'");
			}
			if (!CheckUtil.isNullString(warnSearch.getEndDate())){
				String endDate = warnSearch.getEndDate() + " 23:59:59";
				builder.append(" and t.warnDate <= '").append(endDate).append("'");
			}
		} else if (warnSearch.getSearchType() == 1){ //按照报警类型搜索
			if (!CheckUtil.isNullString(warnSearch.getWarnType())){
				builder.append(" and t.warntype ='").append(warnSearch.getWarnType()).append("'");
			}
		} else if (warnSearch.getSearchType() == 2){ //按照设备搜索
			if (!CheckUtil.isNullString(warnSearch.getDeviceName())){
				builder.append(" and d.deviceName like '%").append(warnSearch.getDeviceName()).append("%'");
			}
		} else if (warnSearch.getSearchType() == 3){ //按照防区搜索
			if (!CheckUtil.isNullString(warnSearch.getZoneName())){
				builder.append(" and z.zoneName like '%").append(warnSearch.getZoneName()).append("%'");
			}
		}
		builder.append(" order by  t.warndate desc");
		
		return pageService.queryForPage(builder.toString(), pn);
	}

	@Override
	public List<?> queryCheckPwdDevices(String userId) {
		return queryDao.queryCheckPwdDevices(userId);
	}

	@Override
	public Map<String, Object> queryPushConfig(String userId) {
		return queryDao.queryPushConfig(userId);
	}

	@Override
	public List<BannerImgDb> getBannerImgs() {
		return queryDao.getBannerImgs();
	}
	
}
