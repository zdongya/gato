package com.yunjing.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunjing.dao.QueryDao;
import com.yunjing.model.Collect;
import com.yunjing.model.Device;
import com.yunjing.model.DeviceGroup;
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
		String sql = "select t.*,c.collectid as collectId from (select * from tb_device  where deviceno in (select deviceno from tb_user_device_map where cuserid=? and istate=1) )t left join tb_collect c on c.deviceNo=t.deviceNo and c.userid=?";
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
			String sql = "select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where t.istate=? and z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid=?) order by t.istate asc, t.warndate desc";
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
			String sql = "select t.*,z.zonename,d.devicename,z.zonecontactor,zonephone,z.zoneLoc from tb_warning_info t,tb_zone z,tb_device d where z.zoneno=t.zoneno and d.deviceno=z.deviceno and z.deviceno in (select deviceno from tb_user_device_map where cuserid=?) order by t.istate asc, t.warndate desc";
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
		String sql = "select count(1) from tb_user_device_map where cuserid=? and deviceno=? and istate=1";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{userId, deviceNo});
		if (count > 0){
			return true;
		} 
		return false;
	}


	@Override
	public boolean bindDeviceCheck(Device device) {
		String sql = "select count(1) from tb_device where deviceno=? and deviceusername=? and devicepwd=?";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{device.getDeviceNo(), device.getDeviceUserName(), device.getDevicePwd()});
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
	public List<?> queryZonesByDeviceNo(String deviceNo) {
		try {
			String sql = "select * from tb_zone where deviceno=?";
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
	
}
