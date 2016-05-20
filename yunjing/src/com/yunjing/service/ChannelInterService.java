package com.yunjing.service;

import java.util.HashMap;
import java.util.HashSet;
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
import com.yunjing.entity.Push;
import com.yunjing.entity.WarningInfo;
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
				} else {
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
	



	public static void main(String[] args) {
//		DeviceDto deviceDto = new DeviceDto();
//		deviceDto.setDeviceNo("abc");
//		deviceDto.setDeviceName("哈哈哈");
//		deviceDto.setDeviceUserName("测试");
//		deviceDto.setDevicePwd("aaa");
//		List<Zone> zones = new ArrayList<Zone>();
//		Zone zone1 = new Zone();
//		zone1.setZoneNo("zone1");
//		zone1.setZoneName("第一防区");
//		zone1.setZoneDesc("前门防区");
//		zone1.setZoneContactor("dongya");
//		zone1.setZonePhone("123456");
//		zone1.setZoneLoc("解放路123号");
//		
//		Zone zone2 = new Zone();
//		zone2.setZoneNo("zone2");
//		zone2.setZoneName("第二防区");
//		zone2.setZoneDesc("后门防区");
//		zone2.setZoneContactor("zhangsan");
//		zone2.setZonePhone("789654");
//		zone2.setZoneLoc("风光路123号");
//		zones.add(zone1);
//		zones.add(zone2);
//		deviceDto.setZoneList(zones);
//		ChannelInterService service = new ChannelInterService();
//		CallResult result = service.deviceActive(deviceDto);
//		System.out.println("code:" + result.getCode() + ";desc:" + result.getDesc());
//		String jsonString = JSON.toJSONString(zones);
//		System.out.println(jsonString);
//		WarningInfo info = new WarningInfo();
//		info.setWarningId("1");
//		info.setWarnDate("2016-03-25 14:27:00");
//		info.setZoneNo("zone1");
//		service.saveWarningInfo(info);
		HashSet<String> ids = new HashSet<String>();
		for (int i=0;i<100;i++){
			String id = Utils.getUUID();
			System.out.println(id);
			ids.add(id);
		}
		System.out.println("size---:" + ids.size());
		
	}



	public CallResult updateZoneInfo(UploadZone uploadZone) {
		SqlSession session = null;
		CallResult callResult = new CallResult();
		try {
			session = MyBatisFactory.getInstance().openSession();
			ChannelInterDao channelInterDao = session.getMapper(ChannelInterDao.class);
			List<?> zoneList = uploadZone.getZoneList();
			for (Object obj:zoneList){
				ZoneDto zone = (ZoneDto) obj;
				zone.setDeviceNo(uploadZone.getDeviceNo());
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
					} else {
						channelInterDao.updateZoneState(zone);
					}
				}
			}
			if (callResult.getCode().equals("0000")){
				session.commit();
				log.info("上传防区信息成功");
			} else {
				session.rollback();
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

}
