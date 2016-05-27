package com.yunjing.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.yunjing.dao.DevicePingDao;
import com.yunjing.util.CallResult;
import com.yunjing.util.MyBatisFactory;

public class DevicePingService {
	private Log log =  LogFactory.getLog("pingService");
	
	public CallResult initPingDate(){
		SqlSession session = null;
		CallResult callResult = new CallResult();
		
		try {
			session = MyBatisFactory.getInstance().openSession();
			DevicePingDao pingDao = session.getMapper(DevicePingDao.class);
			pingDao.initPingDate();
			if (null != session){
				session.commit();
			}
		} catch (Exception e){
			log.error(e.getMessage(), e);
			callResult.setCode("-2000");
			callResult.setDesc("初始话设备心跳日期失败。。。");
			session.rollback();
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
	}
	
	
	public List<String> getOutTimePing(){
		SqlSession session = null;
		try {
			session = MyBatisFactory.getInstance().openSession();
			DevicePingDao pingDao = session.getMapper(DevicePingDao.class);
			return pingDao.getOutTimePing();
		} catch (Exception e) {
			log.error("获取待推送消息失败。。。");
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if (null != session){
				session.close();
			}
		}
	}
	
	
	/**
	 * 设备下线
	 * @param devices
	 * @return
	 */
	public CallResult deviceOffLine(List<String> devices){
		SqlSession session = null;
		CallResult callResult = new CallResult();
		try {
			session = MyBatisFactory.getInstance().openSession();
			DevicePingDao pingDao = session.getMapper(DevicePingDao.class);
			for(String deviceNo:devices){
				pingDao.zoneOffLine(deviceNo);
				pingDao.deviceOffLine(deviceNo);
			}
			if (null != session){
				callResult.setDesc("设备下线成功" + devices + "");
				log.info("设备下线成功。。。");
				session.commit();
			}
		} catch (Exception e){
			log.error(e.getMessage(), e);
			session.rollback();
			callResult.setCode("-1000");
			callResult.setDesc("设备下线失败");
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
		
	}
	
	
	public static void main(String[] args) {
		DevicePingService pingService = new DevicePingService();
//		List<String> devices = pingService.getOutTimePing();
//		System.out.println(devices);
		pingService.initPingDate();
		
	}
	
}
