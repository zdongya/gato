package com.yunjing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.yunjing.dao.ChannelInterDao;
import com.yunjing.dto.DeviceDto;
import com.yunjing.dto.UploadZone;
import com.yunjing.dto.ZoneDto;
import com.yunjing.entity.DevicePing;
import com.yunjing.entity.Push;
import com.yunjing.entity.WarningInfo;
import com.yunjing.entity.Zone;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.DateUtil;
import com.yunjing.util.MyBatisFactory;
import com.yunjing.util.Utils;

public class ChannelInterService {
	private Log log =  LogFactory.getLog("channelInterService");
	
	/**
	 * 激活设备
	 * @param deviceDto
	 * @return
	 */
	public CallResult deviceActive(DeviceDto deviceDto){
		SqlSession session = null;
		CallResult callResult = new CallResult();
		try {
			if (null != deviceDto) {
				session = MyBatisFactory.getInstance().openSession();
				ChannelInterDao channelInterDao = session.getMapper(ChannelInterDao.class);
				int count = channelInterDao.getCountByDeviceNo(deviceDto.getDeviceNo());
				
				if (count ==  0){ //设备未激活
					channelInterDao.saveDevice(deviceDto);
				} else { //已激活判断密码是否有改变
					DeviceDto dbDevice = channelInterDao.getDeviceByNo(deviceDto.getDeviceNo());
					if (!dbDevice.getDevicePwd().equals(deviceDto.getDevicePwd())){ //修改了管理员密码  更新tb_user_device_map 密码校验逻辑
						channelInterDao.updateAdminCheckPwdFlag(deviceDto.getDeviceNo());
					}
					if (CheckUtil.isNullString(deviceDto.getMemberPwd())){ //兼容老版本
						deviceDto.setMemberPwd(dbDevice.getMemberPwd());
					} else { //上传了操作员密码
						String memberPwd = deviceDto.getMemberPwd();
						memberPwd = memberPwd.substring(0, memberPwd.length()-1);
						String[] memberPwds = memberPwd.split(",");
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("deviceNo", deviceDto.getDeviceNo());
						param.put("memberPwds", memberPwds);
						channelInterDao.updateMemberCheckPwdFlag(param);
					}
					
					
					channelInterDao.updateDevice(deviceDto);
				}
				session.commit();
				log.info("上线设备成功。");
			}
		} catch (Exception e){
			log.error("上线设备异常。。。");
			log.error(e.getMessage(), e);
			session.rollback();
			if (callResult.getCode().equals("0000")){
				callResult.setCode("-1000");
				callResult.setDesc("系统异常");
			}
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
		
	}
	
	

	public CallResult saveWarningInfo(WarningInfo info) {
		SqlSession session = null;
		CallResult callResult = new CallResult();
		try {
			session = MyBatisFactory.getInstance().openSession();
			ChannelInterDao channelInterDao = session.getMapper(ChannelInterDao.class);
			int count = channelInterDao.getCountByWaringId(info.getWarningId());
			log.info("报警信息:" + info.toString());
			if (count == 1){ //报警信息已上传
				callResult.setCode("-1000");
				callResult.setDesc("报警信息已上传，不能重复上传");
				log.error("上传了id相同的报警信息，报警编号：" + info.getWarningId());
				throw new RuntimeException();
			} else {
				count = channelInterDao.getCountByZoneNo(info.getZoneNo());
				if (count == 0){
					callResult.setCode("-1000");
					callResult.setDesc("防区信息还没有上传，请先上传防区信息");
					log.error("上传了防区编号不存在的报警信息，报警编号：" + info.getWarningId());
					throw new RuntimeException();
				}
				channelInterDao.saveWarningInfo(info);
				createToClientMqttMsg(channelInterDao, info);
				
			}
			if (null != session){
				log.info("上传报警信息成功，信息编号：" + info.getWarningId());
				session.commit();
			}
		} catch (Exception e){
			log.error(e.getMessage(), e);
			session.rollback();
			if (callResult.getCode().equals("0000")){
				callResult.setCode("-1000");
				callResult.setDesc("系统异常");
			}
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
	}
	
	private void createToClientMqttMsg(ChannelInterDao channelInterDao, WarningInfo info) {
		List<String> userIds = channelInterDao.getZoneUsers(info.getZoneNo());
		if (null != userIds && userIds.size() > 0) {
			String zoneName = channelInterDao.getZoneNameByZoneNo(info.getZoneNo());
			Map<String, String> map = new HashMap<String, String>();
			map.put("action", "warn");
			map.put("zoneNo", info.getZoneNo());
			map.put("zoneName", zoneName);
			map.put("warnDate", info.getWarnDate());
			map.put("warnType", info.getWarnType());
			Push push = new Push();
			push.setAddDate(DateUtil.getNowDateTime());
			push.setIstate("0");
			push.setItype("2");
			push.setMsgText(JSONObject.toJSONString(map));
			push.setPushService("2");
			push.setZoneNo(info.getZoneNo());
			for (String userId:userIds){
				push.setMsgId(Utils.getUUID());
				push.setTopic(userId);
				channelInterDao.savePushMsg(push);
			}
			
		}
		
	}
	



	public static void main(String[] args)  throws Exception{
//		SqlSession session = MyBatisFactory.getInstance().openSession();
//		ChannelInterDao channelInterDao = session.getMapper(ChannelInterDao.class);
		
		ChannelInterService service = new ChannelInterService();
		DeviceDto dto = new DeviceDto();
		dto.setDeviceNo("200bccce356d0240");
		dto.setDevicePwd("admin");
		dto.setDeviceUserName("admin");
		dto.setMemberPwd("111111,222,333,");
		dto.setDeviceVersion("1.0");
		dto.setOnline("1");
		service.deviceActive(dto);
//		Zone zone = new Zone();
//		zone.setZoneNo("200bccce356d0019");
	}



	public CallResult updateZoneInfo(UploadZone uploadZone) {
		SqlSession session = null;
		CallResult callResult = new CallResult();
		try {
			session = MyBatisFactory.getInstance().openSession();
			ChannelInterDao channelInterDao = session.getMapper(ChannelInterDao.class);
			DeviceDto device = channelInterDao.getDeviceByNo(uploadZone.getDeviceNo());
			log.info("deviceNo:" + device.getDeviceNo() + ";online:" + device.getOnline());
			if (device.getOnline().equals("0")){ //设备是下线状态不允许上传防区信息
				callResult.setCode("-6666");
				callResult.setDesc("你所上传防区所在的设备是下线状态，不能上传防区");
				log.error("设备【" + uploadZone.getDeviceNo() + "】，状态为下线，不允许上传防区");
			} else {
				if (uploadZone.getbInit().equals("0")){
					uploadZone(uploadZone, callResult, channelInterDao);
				} else {
					initZone(uploadZone, callResult, channelInterDao);
				}
				if (callResult.getCode().equals("0000")){
					session.commit();
					log.info("上传防区信息成功");
				} else {
					session.rollback();
				}
			}
			
		} catch (Exception e) {
			log.error("上传防区信息异常");
			log.error(e.getMessage(), e);
			callResult.setCode("-6000");
			callResult.setDesc("系统异常");
			session.rollback();
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
	}


	
	/**
	 * 防区初始化防区存在则修改，不存在则添加  减少则删除
	 * @param uploadZone
	 * @param callResult
	 * @param channelInterDao
	 */
	private void initZone(UploadZone uploadZone, CallResult callResult, ChannelInterDao channelInterDao) {
		List<?> zones = uploadZone.getZoneList();
		List<String> zoneIds = new ArrayList<String>();
		List<String> deleteZones = null;
		if (null == zones || zones.size() < 1){ //删除所有防区
			deleteZones = channelInterDao.getDeviceZones(uploadZone.getDeviceNo());
		} else {
			for (Object obj:zones){
				ZoneDto zone = (ZoneDto) obj;
				zone.setDeviceNo(uploadZone.getDeviceNo());
				if (CheckUtil.isNullString(zone.getZoneName())){
					String str = zone.getZoneNo();
					String zoneName = str.substring(str.length()-3, str.length());
					zone.setZoneName(zoneName);
				}
				int num = channelInterDao.getCountByZoneNo(zone.getZoneNo());
				if (num == 0){
					channelInterDao.saveZone(zone);
				} else {
					channelInterDao.updateZone(zone);
				}
				zoneIds.add(zone.getZoneNo());
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("deviceNo", uploadZone.getDeviceNo());
			map.put("zones", zoneIds);
			deleteZones = channelInterDao.getInitNotExsitZones(map);
		}
		
		if (null != deleteZones && deleteZones.size() > 0){ //删除防区
			for (String zoneNo:deleteZones){
				channelInterDao.cleanWarningInfo(zoneNo); //清除关联关系
				channelInterDao.cleanLog(zoneNo);
				channelInterDao.deleteZone(zoneNo);
			}
		}
		
	}



	private void uploadZone(UploadZone uploadZone, CallResult callResult, ChannelInterDao channelInterDao) {
		List<?> zoneList = uploadZone.getZoneList();
		for (Object obj:zoneList){
			ZoneDto zone = (ZoneDto) obj;
			zone.setDeviceNo(uploadZone.getDeviceNo());
			if (CheckUtil.isNullString(zone.getZoneName())){
				String str = zone.getZoneNo();
				String zoneName = str.substring(str.length()-3, str.length());
				zone.setZoneName(zoneName);
			}
			
			if(CheckUtil.isNullString(zone.getUploadType()) || "0,1,2".indexOf(zone.getUploadType()) == -1){
				callResult.setCode("-1000");
				callResult.setDesc("通道uploadType不正确，不能上传");
				break;
			} else {
				if (zone.getUploadType().equals("0")){ //添加或修改防区
					int count = channelInterDao.getCountByZoneNo(zone.getZoneNo());
					if (count == 0){
						channelInterDao.saveZone(zone);
					} else {
						channelInterDao.updateZone(zone);
					}
				} else if (zone.getUploadType().equals("1")) { //删除防区
					channelInterDao.cleanWarningInfo(zone.getZoneNo()); //清除关联关系
					channelInterDao.cleanLog(zone.getZoneNo());
					channelInterDao.deleteZone(zone.getZoneNo());
				} else { //更新防区状态
					channelInterDao.updateZoneState(zone);
				}
			}
		}
	}

	public CallResult devicePing(DevicePing devicePing){
		
		SqlSession session = null;
		CallResult callResult = new CallResult();
		try {
			session = MyBatisFactory.getInstance().openSession();
			ChannelInterDao channelInterDao = session.getMapper(ChannelInterDao.class);
			int count = channelInterDao.getCountByDeviceNo(devicePing.getDeviceNo());
			if (count == 0){
				callResult.setCode("-2000");
				callResult.setDesc("错误的防区编号:" + devicePing.getDeviceNo());
			} else {
				channelInterDao.saveOrUploadDevicePing(devicePing);
			}
			
			if (callResult.getCode().equals("0000")){
				session.commit();
				log.info("设备【" + devicePing.getDeviceNo() + "】和服务器之间心跳正常");
			} else {
				session.rollback();
			}
			
		} catch (Exception e) {
			log.error("调用心跳接口异常");
			log.error(e.getMessage(), e);
			callResult.setCode("-6000");
			callResult.setDesc("系统异常");
			session.rollback();
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
	}
	

}
