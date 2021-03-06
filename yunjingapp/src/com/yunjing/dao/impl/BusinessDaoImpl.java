package com.yunjing.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunjing.dao.BusinessDao;
import com.yunjing.model.Collect;
import com.yunjing.model.DeviceGroup;
import com.yunjing.model.OperatorLog;
import com.yunjing.model.Push;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.util.Utils;
@Repository(value = "businessDao")
public class BusinessDaoImpl implements BusinessDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void saveDeviceGroup(DeviceGroup deviceGroup) {
		String sqlStr = "insert into tb_device_group(groupid,groupname,istate,operator,cadddate) values(?,?,1,?,now())";
		Object[] params = new Object[] {Utils.getUUID(), deviceGroup.getGroupName(),deviceGroup.getUserId()};
		jdbcTemplate.update(sqlStr, params);
	}

	@Override
	public void hanleWarnings(WarningInfo warningInfo) {
		String sql = "update tb_warning_info set istate=?,memo=?,handler=?,handledate=now() where warningid=?";
		Object[] params = new Object[] {warningInfo.getIstate(), warningInfo.getMemo(),warningInfo.getUserId(),warningInfo.getWarningId()};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void saveCollect(Collect collect) {
		String sql = "insert into tb_collect(collectid,userid,deviceno,adddate) values(?,?,?,now())";
		Object[] params = new Object[] {collect.getCollectId(), collect.getUserId(), collect.getDeviceNo()};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void saveUserDevice(String userId, String deviceNo) {
		String sql = "insert into tb_user_device_map (cuserid,deviceno,binddate,istate) values(?,?,now(),1)";
		Object[] params = new Object[] {userId, deviceNo};
		jdbcTemplate.update(sql, params);
	}
	
	public void bindDevice(String userId, String deviceNo){
		String sql = "update tb_user_device_map set istate=1,binddate=now() where cuserid=? and deviceno=?";
		Object[] params = new Object[] {userId, deviceNo};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void deleteCollect(String collectId) {
		String sql = "delete from tb_collect where collectid=?";
		Object[] params = new Object[] {collectId};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void updateZone(Zone zone) {
		String sql = "update tb_zone set zonename=?,zonecontactor=?,zonephone=?,zoneloc=?,zonedesc=? where zoneno=?";
		Object[] params = new Object[] {zone.getZoneName(), zone.getZoneContactor(), zone.getZonePhone(), zone.getZoneLoc(), zone.getZoneDesc(), zone.getZoneNo()};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void saveRetroaction(String userId, String contents, String contact) {
		String id = Utils.getUUID();
		String sqlStr = "insert into tb_retroaction(id,userid,contents,contact,adddate) values(?,?,?,?,now())";
		Object[] params = new Object[] {id, userId, contents, contact};
		jdbcTemplate.update(sqlStr, params);
		
	}

	@Override
	public void changeZoneState(String zoneNo, String istate) {
		String sql = "update tb_zone set zonestate=? where zoneno=?";
		Object[] params = new Object[] {istate, zoneNo};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void deleteNotPushInvalidMsg(String topic, String zoneNo, String itype) {
		String sql = "delete from tb_push where topic=? and zoneno=? and itype=? and istate=0";
		Object[] params = new Object[]{topic, zoneNo, itype};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void savePushMsg(Push push) {
		String sql = "insert into tb_push(msgid,msgtext,topic,adddate,istate,pushservice,zoneno,itype) values (?,?,?,?,0,?,?,?)" ;
		Object[] params = new Object[]{push.getMsgId(), push.getMsgText(), push.getTopic(), push.getAddDate(), push.getPushService(), push.getZoneNo(), push.getItype()};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void unbindDevice(String userId, String deviceNo) {
		String sql = "update tb_user_device_map set istate=0 where cuserid=? and deviceno=?";
		Object[] params = new Object[]{userId, deviceNo};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void editDeviceName(String deviceNo, String deviceName) {
		String sql = "update tb_device set devicename=? where deviceno=?";
		Object[] params = new Object[]{deviceName, deviceNo};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void addOperatorLog(OperatorLog log) {
		String sql = "insert into tb_log (id,memo,operatorType,createTime,ipAddr,deviceNo,zoneNo,memberId) values(?,?,?,now(),?,?,?,?)";
		Object[] params = new Object[]{log.getId(), log.getMemo(), log.getOperatorType(), log.getIpAddr(), log.getDeviceNo(), log.getZoneNo(), log.getMemberId()};
		jdbcTemplate.update(sql, params);
		System.out.println("添加信息到操作日志表成功。。。");
	}


}
