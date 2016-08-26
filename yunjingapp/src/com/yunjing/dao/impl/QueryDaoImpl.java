package com.yunjing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunjing.dao.QueryDao;
import com.yunjing.dto.DeviceCheckDto;
import com.yunjing.model.Collect;
import com.yunjing.model.Device;
import com.yunjing.model.DeviceGroup;
import com.yunjing.model.Sms;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.util.CheckUtil;

@Repository(value="queryDao")
public class QueryDaoImpl implements QueryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Device queryDeviceById(String deviceNo) {
		try {
			String sql = "select * from tb_device where deviceno=?";
			return (Device)jdbcTemplate.queryForObject(sql, new Object[]{deviceNo},new BeanPropertyRowMapper(Device.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Zone queryZoneById(String zoneNo) {
		try {
			String sql = "select t.*,d.deviceName  from tb_zone t, tb_device d where t.zoneno=? and t.deviceno=d.deviceno";
			return (Zone)jdbcTemplate.queryForObject(sql, new Object[]{zoneNo},new BeanPropertyRowMapper(Zone.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<?> queryUserDevices(String userId) {
//		String sql = "select * from tb_device where deviceno in (select deviceno from tb_user_device_map where cuserid=? and istate=1)";
//		String sql = "select t.*,c.collectid as collectId from (select * from tb_device  where deviceno in (select deviceno from tb_user_device_map where cuserid=? and istate=1) )t left join tb_collect c on c.deviceNo=t.deviceNo and c.userid=?";
		String sql = "SELECT t.*,c.collectid,(SELECT COUNT(1) FROM tb_zone WHERE deviceno=t.deviceno) AS zoneCount FROM (SELECT t.*,a.itype as userType,checkPwdFlag FROM (SELECT * FROM tb_user_device_map m WHERE m.CUSERID=? AND m.ISTATE=1 ) a,tb_device t WHERE t.deviceNo=a.deviceNo)  "
				+ " t LEFT JOIN tb_collect c ON c.deviceNo=t.deviceNo AND c.userid=?";
		return jdbcTemplate.query(sql, new Object[]{userId, userId},new BeanPropertyRowMapper(Device.class));
	}


	@Override
	public int queryDeviceGroupCountByName(String groupName) {
		String sql = "select count(*) from tb_device_group where groupname=?";
		return jdbcTemplate.queryForInt(sql,groupName);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DeviceGroup> queryDeviceGroups(String userId) {
		String sql = "select * from tb_device_group where operator=? ";
		return jdbcTemplate.query(sql, new Object[]{userId},new BeanPropertyRowMapper(DeviceGroup.class));
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WarningInfo> queryWarning(int istate, String userId) {
		try {
			String sql = "select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where t.istate=? and z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid=? and istate=1) order by t.istate asc, t.warndate desc";
			return jdbcTemplate.query(sql, new Object[]{istate, userId},new BeanPropertyRowMapper(WarningInfo.class));
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WarningInfo> queryWarning(String userId) {
		try {
			String sql = "select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid=? and istate=1) order by t.istate asc, t.warndate desc";
			return jdbcTemplate.query(sql,new Object[]{userId},new BeanPropertyRowMapper(WarningInfo.class));
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public WarningInfo getWarningsById(String warningId) {
		try {
			String sql = "select * from tb_warning_info where warningid=?";
			return (WarningInfo)jdbcTemplate.queryForObject(sql, new Object[]{warningId},new BeanPropertyRowMapper(WarningInfo.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Collect> getUserCollects(String userId) {
		try {
			String sql = "select t.*,d.devicename from tb_collect t,tb_device d where t.userid=? and d.deviceno=t.deviceno";
			return jdbcTemplate.query(sql, new Object[]{userId},new BeanPropertyRowMapper(Collect.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@Override
	public int countCollectByUserAndDevice(String userId, String deviceNo) {
		String sql = "select count(*) from tb_collect where userid=? and deviceno=?";
		return jdbcTemplate.queryForInt(sql,new Object[]{userId, deviceNo});
	}


	@Override
	public boolean queryUserBindDeviceFlag(String deviceNo, String userId) {
		String sql = "select count(1) from tb_user_device_map where cuserid=? and deviceno=? and istate=1 and CHECKPWDFLAG=0";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{userId, deviceNo});
		if (count > 0){
			return true;
		} 
		return false;
	}


	@Override
	public boolean bindDeviceCheck(Device device) {
		String type = device.getUserType();
		boolean newVersion = true;
		String sql = "select count(1) from tb_device where deviceno=? and devicepwd=?";
		if (type.equals("0")){ //新版管理员
			sql = "select count(1) from tb_device where deviceno=? and devicepwd=?";
		} else if (type.equals("1")){ //新版操作员
			device.setDevicePwd("%" + device.getDevicePwd() + ",%");
			sql = "select count(1) from tb_device where deviceno=? and memberpwd like ? ";
		} else { //旧版
			newVersion = false;
		}
		int count;
		if (newVersion){
			count = jdbcTemplate.queryForInt(sql,new Object[]{device.getDeviceNo(), device.getDevicePwd()});
		} else {
			count = jdbcTemplate.queryForInt(sql,new Object[]{device.getDeviceNo(), device.getDevicePwd()});
		}
		
		if (count > 0){
			return true;
		} 
		return false;
	}

	@Override
	public boolean checkDeviceByNo(String deviceNo) {
		String sql = "select count(1) from tb_device where deviceno=?";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{deviceNo});
		if (count > 0){
			return true;
		} 
		return false;
	}


	@Override
	public int getCollectCountById(String collectId, String userId, String deviceNo) {
		String sql = "select count(1) from tb_collect where collectid=? and userid=? and deviceno=?";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{collectId, userId, deviceNo});
		return count;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<?> queryZonesByDeviceNo(String userId, String deviceNo) {
		try {
			String sql = "select t.*,d.deviceName from tb_zone t,tb_device d  where t.deviceno=? and d.deviceno=t.deviceno order by adddate desc";
			if (!CheckUtil.isNullString(userId)){ //查询用户对应防区的管理权限  管理员还是操作员
				sql = "SELECT t.*,m.itype AS userType,d.deviceName FROM tb_zone t,tb_user_device_map m,tb_device d WHERE t.deviceno=? and m.deviceno=t.deviceno and d.deviceno=t.deviceno AND m.cuserid='" + userId + "'" ;
			}
			return jdbcTemplate.query(sql,new Object[]{deviceNo},new BeanPropertyRowMapper(Zone.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@Override
	public int checkOwnZone(Zone zone) {
		String sql = "select count(1) from tb_zone where zoneno=? and deviceno in (select deviceno from tb_user_device_map where cuserid=? and istate=1)";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{zone.getZoneNo(), zone.getUserId()});
		return count;
	}


	@Override
	public int checkZoneStateChange(String zoneNo, String istate) {
		String sql = "select count(1) from tb_zone where zoneno=? and (zonestate!=? or zonestate is null)";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{zoneNo, istate});
		return count;
	}
	
	public int checkWarningStateChange(String warningId, String istate) {
		if (CheckUtil.isNullString(istate)){
			istate = "";
		}
		String sql = "select count(1) from tb_warning_info where warningid=? and istate!=?";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{warningId	, istate});
		return count;
	}


	@Override
	public boolean checkIsBindDevice(String userId, String deviceNo) {
		String sql = "select count(1) from tb_user_device_map where cuserid=? and deviceno=? and istate=1";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{userId, deviceNo});
		if (count == 1){
			return true;
		} else {
			return false;
		}
	}


	@Override
	public boolean haveBindDataCheck(String userId, String deviceNo) {
		String sql = "select count(1) from tb_user_device_map where cuserid=? and deviceno=?";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{userId, deviceNo});
		if (count == 1){
			return true;
		} else {
			return false;
		}
	}


	@Override
	public Map<String, String> queryIndexData(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		String userDeviceCountSql = "select count(1) from tb_user_device_map where cuserid=? and istate=1";
		String userZoneCountSql = "select count(1) from tb_zone where deviceno in (select deviceno from tb_user_device_map where cuserid=? AND istate=1)";
		String queryImgSql = "select icoin as headImg from tb_user where userid=?";
		int userDeviceCount = jdbcTemplate.queryForInt(userDeviceCountSql,new Object[]{userId});
		int userZoneCount = jdbcTemplate.queryForInt(userZoneCountSql,new Object[]{userId});
		@SuppressWarnings("unchecked")
		String headImg = (String)jdbcTemplate.queryForObject(queryImgSql, new Object[]{userId}, String.class);
		map.put("deviceCount", String.valueOf(userDeviceCount));
		map.put("zoneCount", String.valueOf(userZoneCount));
		map.put("code", "10000");
		map.put("headImg", headImg);
		map.put("desc", "查询成功");
		return map;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Sms> getNotSendSms(String service) {
		try {
			String queryString = "select * from tb_sms where flag=0 and service = ? order by addDate asc";
			return jdbcTemplate.query(queryString,new Object[]{service},new BeanPropertyRowMapper(Sms.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@Override
	public List<Sms> getSmsReportList(String service) {
		try {
			String queryString = "select * from tb_sms where flag=1 and service = ? order by addDate asc";
			return jdbcTemplate.query(queryString,new Object[]{service},new BeanPropertyRowMapper(Sms.class));
		} catch (DataAccessException e) {
			return null;
		}
	}


	@Override
	public boolean checkOwnManageDevice(String userId, String deviceNo) {
		String sql = "select count(1) from tb_user_device_map where cuserid=? and deviceno=? and istate=1";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{userId, deviceNo});
		if (count == 1){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkDeviceDefencePwd(String deviceNo, String pwd){
		String sql = "select count(1) from tb_device where deviceno=? and (devicePwd=? or memberpwd like ?) ";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{deviceNo, pwd, "%" + pwd + ",%" });
		if (count == 1){
			return true;
		} else {
			return false;
		}
		
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<?> queryCheckPwdDevices(String userId) {
		try {
			String queryString = "SELECT t.deviceNo,d.deviceName,t.ITYPE AS userType FROM tb_user_device_map t,tb_device d WHERE d.deviceNo=t.deviceno AND t.cuserid=? AND t.istate=1 AND t.checkPwdFlag=1 ";
			return jdbcTemplate.query(queryString, new Object[]{userId},new BeanPropertyRowMapper(DeviceCheckDto.class));
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public int countPushConfig(String userId) {
		String sql = "select count(*) from tb_push_config where userId=?";
		return jdbcTemplate.queryForInt(sql,userId);
	}


	@Override
	public Map<String, Object> queryPushConfig(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "10000");
		map.put("desc", "查询成功");
		int count = countPushConfig(userId);
		if (count == 0){
			map.put("itype", 2);
		} else {
			String querySql = "select itype from tb_push_config where userId=?";
			int itype = jdbcTemplate.queryForInt(querySql,new Object[]{userId});
			map.put("itype", itype);
		}
		
		
		return map;
	}
	
}
