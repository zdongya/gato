package com.yunjing.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunjing.dao.BusinessDao;
import com.yunjing.dao.QueryDao;
import com.yunjing.dto.PushDto;
import com.yunjing.model.Collect;
import com.yunjing.model.Device;
import com.yunjing.model.OperatorLog;
import com.yunjing.model.Push;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.service.BusinessService;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.DateUtil;
import com.yunjing.util.SmsSender;
import com.yunjing.util.Utils;
@Service(value = "businessService")
public class BusinessServiceImpl implements BusinessService {
	private static Logger logger = Logger.getLogger(BusinessService.class);
	
	@Autowired
	private QueryDao queryDao;
	@Autowired
	private BusinessDao businessDao;
	
	@Transactional
	public CallResult handleWarnings(WarningInfo warningInfo){
		CallResult result = new CallResult();
		WarningInfo dbInfo = queryDao.getWarningsById(warningInfo.getWarningId());
		if (null == dbInfo){
			result.setCode("-3000");
			result.setDesc("警报不存在");
		} else {
			Zone zone = new Zone();
			zone.setUserId(warningInfo.getUserId());
			zone.setZoneNo(warningInfo.getZoneNo());
			int count = queryDao.checkOwnZone(zone);
			if (count != 1){
				result.setCode("-1000");
				result.setDesc("您没有修改该防区的报警状态的权限");
			} else {
				count = queryDao.checkWarningStateChange(warningInfo.getWarningId(), String.valueOf(warningInfo.getIstate()));
				if (count != 1){
					result.setCode("-2000");
					result.setDesc("防区报警状态未修改");
				} else {
					Zone zoneDb = queryDao.queryZoneById(warningInfo.getZoneNo());
					Device deviceDb = queryDao.queryDeviceById(zoneDb.getDeviceNo());
					if (deviceDb.getOnline().equals("0")){
						result.setCode("-3000");
						result.setDesc("离线防区不能操作");
					} else {
						businessDao.hanleWarnings(warningInfo);
						Push push = new Push();
						push.setMsgId(Utils.getUUID());
						push.setAddDate(DateUtil.getNowDateTime());
						push.setPushService("0"); //MQTT推送
						push.setItype("0"); //消息类型为消警
						push.setTopic(zoneDb.getDeviceNo()); //topic 为设备编号
						push.setZoneNo(warningInfo.getZoneNo());
						businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getZoneNo(), push.getItype()); //删除无效信息
						
						PushDto dto = new PushDto();
						BeanUtils.copyProperties(push, dto);
						dto.setWarningId(warningInfo.getWarningId());
						dto.setTime(System.currentTimeMillis() + "");
						dto.setCommandState(String.valueOf(warningInfo.getIstate()));
						push.setMsgText(Utils.wrapPushMsg(dto));
						logger.info("msgText:" + push.getMsgText());
						
						businessDao.savePushMsg(push);
						logger.info("修改报警状态成功，防区编号：" + warningInfo.getZoneNo() + ";处理后状态为：" + warningInfo.getIstate());
					
						//添加操作日志
						OperatorLog log = new OperatorLog();
						log.setDeviceNo(zoneDb.getDeviceNo());
						log.setIpAddr(warningInfo.getIpAddr());
						log.setMemberId(warningInfo.getUserId());
						log.setMemo("报警编号:" + warningInfo.getWarningId() + "被处理，处理后的状态为:" + (warningInfo.getIstate().intValue()==1?"已解决":"误报"));
						log.setOperatorType("1"); //报警处理
						log.setZoneNo(warningInfo.getZoneNo());
						saveOperatorLog(log);
					}
				}
			}
		}
		
		return result;
		 
	}

	@Override
	@Transactional
	public CallResult addCollects(String userId, String deviceNo) {
		CallResult result = new CallResult();
		int nums = queryDao.countCollectByUserAndDevice(userId, deviceNo);
		if (nums != 0){
			result.setCode("-1000");
			result.setDesc("已经收藏过");
		} else {
			Collect collect = new Collect();
			collect.setUserId(userId);
			collect.setDeviceNo(deviceNo);
			collect.setCollectId(Utils.getUUID());
			businessDao.saveCollect(collect);
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult bindDevice(Device device) {
		CallResult result = new CallResult();
		String rightPwd = device.getDevicePwd();
		if (CheckUtil.isNullString(device.getDeviceNo()) || CheckUtil.isNullString(device.getDevicePwd())){
			result.setCode("-1000");
			result.setDesc("参数错误。");
		} else {
			if (queryDao.queryUserBindDeviceFlag(device.getDeviceNo(), device.getUserId())){ //判断是否添加过该设备(如果需要校验密码则认为未添加)
				result.setCode("-2000");
				result.setDesc("您已经添加过本设备不能重复添加。");
			} else {
				boolean deviceExistFlag = queryDao.checkDeviceByNo(device.getDeviceNo());
				if (!deviceExistFlag){ //设备不存在
					result.setCode("-3000");
					result.setDesc("设备不存在");
				} else {
					String itype = device.getUserType();
					if (CheckUtil.isNullString(itype)){
						itype = "0";
					}
					
					boolean bindCheck = queryDao.bindDeviceCheck(device); //信息正确
					if (bindCheck){
						
						if (queryDao.haveBindDataCheck(device.getUserId(), device.getDeviceNo())){ //曾经绑定过
							businessDao.bindDevice(device.getUserId(), device.getDeviceNo(), Integer.parseInt(itype), rightPwd);
						} else { //未绑定过
							businessDao.saveUserDevice(device.getUserId(), device.getDeviceNo(), Integer.parseInt(itype), rightPwd);
						}
						if (!CheckUtil.isNullString(device.getDeviceName())){
							businessDao.editDeviceName(device.getDeviceNo(), device.getDeviceName());
						}
						result.setDesc("绑定设备成功");
					} else {
						result.setCode("-4000");
						result.setDesc("设备不存在或密码错误");
					}
				}
			}
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult cancelCollects(String collectId, String userId, String deviceNo) {
		CallResult result = new CallResult();
		int count = queryDao.getCollectCountById(collectId, userId, deviceNo);
		if (count < 1 ){
			result.setCode("-1000");
			result.setDesc("您还未收藏本设备，取消无效");
		} else {
			businessDao.deleteCollect(collectId);
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult editZone(Zone zone) {
		CallResult result = new CallResult();
		
		int count = queryDao.checkOwnZone(zone);
		if (count != 1){
			result.setCode("-1000");
			result.setDesc("该防区不属于您管理，您无权修改");
		} else {
			businessDao.updateZone(zone);
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult addRetroaction(String userId, String contents, String contact) {
		CallResult result = new CallResult();
		if (CheckUtil.isNullString(contents)){
			result.setCode("-1000");
			result.setDesc("反馈内容不能为空");
		} else {
			businessDao.saveRetroaction(userId, contents, contact);
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult defence(String userId, String zoneNo, String istate, String ipAddr) {
		CallResult result = new CallResult();
		if (CheckUtil.isNullString(istate) || "1,2".indexOf(istate) == -1){ //布撤防状态不正确
			result.setCode("-3000");
			result.setDesc("布撤防状态不正确");
			return result;
		}
		Zone zone = new Zone();
		zone.setZoneNo(zoneNo);
		zone.setUserId(userId);
		int count = queryDao.checkOwnZone(zone);
		if (count != 1){
			result.setCode("-1000");
			result.setDesc("您无权修改该防区的布撤防状态");  
		} else {
			Zone zoneDb = queryDao.queryZoneById(zoneNo);
			if (zoneDb.getZoneOnline().equals("0")){
				result.setCode("-3000");
				result.setDesc("防区未上线");
			} else {
				if("1,2,3".indexOf(zoneDb.getZoneStyle()) !=-1){
					result.setCode("-1234");
					result.setDesc("24小时防区不允许布撤防");
				} 
				
//				else {
//					if (CheckUtil.isNullString(zoneDb.getZoneState()) || "1,2".indexOf(zoneDb.getZoneState())!=-1){
//						result.setCode("-4000");
//						result.setDesc("不允许修改该防区的布撤防状态");
//					} else {
//						if (istate.equals("1")&& !zoneDb.getZoneState().equals("4")){
//							result.setCode("-5000");
//							result.setDesc("该防区的状态为已撤防状态不能改为撤防中");
//						} 
//						if (istate.equals("2")&& !zoneDb.getZoneState().equals("3")){
//							result.setCode("-6000");
//							result.setDesc("该防区的状态为已布防状态不能改为布防中");
//						}
//					}
//				}
				
			}
			
			if (result.getCode().equals("10000")){ //正确
				
				//不更新状态
//				businessDao.changeZoneState(zoneNo, istate);
				
				Push push = new Push();
				push.setMsgId(Utils.getUUID());
				push.setAddDate(DateUtil.getNowDateTime());
				push.setPushService("0"); //MQTT推送
				push.setItype("1"); //消息类型为布撤防
				push.setTopic(zoneDb.getDeviceNo()); //topic 为设备编号
				push.setZoneNo(zoneNo);
				
				businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getZoneNo(), push.getItype()); //删除无效信息
				
				String pushZoneState = "1"; //推送防区要进行的布撤防操作 默认要防区布防
				String operatorType = "2";
				if (istate.equals("1")){ //app为撤防中
					pushZoneState = "0";
					operatorType = "3"; //撤防操作
				}
				
				PushDto dto = new PushDto();
				BeanUtils.copyProperties(push, dto);
				dto.setTime(System.currentTimeMillis() + "");
				dto.setCommandState(pushZoneState);
				push.setMsgText(Utils.wrapPushMsg(dto));
				logger.info("msgText:" + push.getMsgText());
				businessDao.savePushMsg(push);
				logger.info("修改布撤防状态成功，防区编号：" + zoneNo + ";处理后状态为：" + istate);
				
				
				//添加操作日志
				OperatorLog log = new OperatorLog();
				log.setDeviceNo(zoneDb.getDeviceNo());
				log.setIpAddr(ipAddr);
				log.setMemberId(userId);
				log.setMemo("用户进行" + (istate.equals("1")?"撤防":"布防") + "操作" );
				log.setOperatorType(operatorType); //操作类型
				log.setZoneNo(zoneNo);
				saveOperatorLog(log);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult unbindDevice(String userId, String deviceNo) {
		CallResult result = new CallResult();
		boolean flag = queryDao.checkIsBindDevice(userId, deviceNo);
		if (!flag){
			result.setCode("-1000");
			result.setDesc("您未绑定该设备");
		} else {
			businessDao.unbindDevice(userId, deviceNo);
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult editDeviceName(String userId, String deviceNo, String deviceName) {
		CallResult result = new CallResult();
		businessDao.editDeviceName(deviceNo, deviceName);
		return result;
	}

	@Override
	public void saveOperatorLog(OperatorLog log) {
		businessDao.addOperatorLog(log);
	}

	@Override
	@Transactional
	public void updateSms(SmsSender sender) {
		if (sender.getItype() == 0){ //提交短信
			businessDao.updateSmsCommitResult(sender);
		} else {
			businessDao.updateSmsSendResult(sender);
		}
		
	}

	@Override
	@Transactional
	public void updateOverTimeSms() {
		businessDao.updateNotSendSmsToFail();
		businessDao.updateNotGetReportToSuccess();
		
	}

	@Override
	@Transactional
	public CallResult deviceDefence(String userId,String pwd,String deviceNo, String istate, String ipAddr) {
		CallResult result = new CallResult();
		if (CheckUtil.isNullString(istate) || "1,2".indexOf(istate) == -1){ //布撤防状态不正确
			result.setCode("-3000");
			result.setDesc("布撤防状态不正确");
			return result;
		}
		boolean flag = queryDao.checkOwnManageDevice(userId, deviceNo); //是否是自己管理的防区
		if (!flag){
			result.setCode("-1000");
			result.setDesc("不是自己的防区不允许操作");  
		} else {
			//判断密码是否正确
			flag = queryDao.checkDeviceDefencePwd(deviceNo, pwd);
			if (!flag){
				result.setCode("-4000");
				result.setDesc("密码错误");  
				return result;
			}
			
			List<?> zones = queryDao.queryZonesByDeviceNo(null,deviceNo);
			if (null != zones && zones.size()>0){
				int num = 0;
				for (int i=0; i<zones.size(); i++){
					Zone zone = (Zone)zones.get(i);
					if ("1,2,3".indexOf(zone.getZoneStyle()) != -1 || zone.getZoneOnline().equals("0")){
						logger.info("未上线的防区和24小时防区，布撤防不需要处理");
					} else {
						num ++;
						//不更新状态
//						businessDao.changeZoneState(zone.getZoneNo(), istate);
					}
				}
				if (num == 0){
					result.setCode("-6000");
					result.setDesc("该设备里面的所有防区都是24h防区，本次操作无效");  
					return result;
				}
				
				//把一键布撤防消息加入MQTT推送表
				Push push = new Push();
				push.setMsgId(Utils.getUUID());
				push.setAddDate(DateUtil.getNowDateTime());
				push.setPushService("0"); //MQTT推送
				push.setItype("3"); //消息类型为布撤防
				push.setTopic(deviceNo); //topic 为设备编号
				businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getItype()); //删除无效一键布撤防推送消息
				String pushZoneState = "1"; //推送防区要进行的布撤防操作 默认要一键布防
				String operatorType = "4"; //默认一键布防
				if (istate.equals("1")){ //app为撤防中
					pushZoneState = "0";
					operatorType = "5"; //一键撤防操作
				}
				
				PushDto dto = new PushDto();
				BeanUtils.copyProperties(push, dto);
				dto.setDeviceNo(deviceNo);
				dto.setTime(System.currentTimeMillis() + "");
				dto.setCommandState(pushZoneState);
				push.setMsgText(Utils.wrapPushMsg(dto));
				logger.info("msgText:" + push.getMsgText());
				businessDao.savePushMsg(push);
				logger.info("一键布撤防操作成功，设备编号：" + deviceNo + ";处理后状态为：" + istate);
				
				//添加操作日志
				OperatorLog log = new OperatorLog();
				log.setDeviceNo(deviceNo);
				log.setIpAddr(ipAddr);
				log.setMemberId(userId);
				log.setMemo("用户进行" + (istate.equals("1")?"一键撤防":"一键布防") + "操作" );
				log.setOperatorType(operatorType); //操作类型
				saveOperatorLog(log);
			} else {
				result.setCode("-2000");
				result.setDesc("您的设备还没有防区，请先上传");  ;
			}
			
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult deviceHandleWaring(String userId, String ipAddr) {
		CallResult result = new CallResult();
		List<?> deviceList = queryDao.queryUserDevices(userId);
		if (null != deviceList && deviceList.size() > 0){
			for(Object obj:deviceList){
				Device device = (Device)obj;
				String deviceNo = device.getDeviceNo();
				Push push = new Push();
				push.setMsgId(Utils.getUUID());
				push.setAddDate(DateUtil.getNowDateTime());
				push.setPushService("0"); //MQTT推送
				push.setItype("4"); //消息类型为一键消警
				push.setTopic(deviceNo); //topic 为设备编号
				businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getItype()); //删除无效一键消警推送消息
				
				PushDto dto = new PushDto();
				BeanUtils.copyProperties(push, dto);
				dto.setDeviceNo(deviceNo);
				dto.setTime(System.currentTimeMillis() + "");
				push.setMsgText(Utils.wrapPushMsg(dto));
				logger.info("msgText:" + push.getMsgText());
				businessDao.savePushMsg(push);
				logger.info("一键消警操作中，设备编号：" + deviceNo);
				
				//添加操作日志
				OperatorLog log = new OperatorLog();
				log.setDeviceNo(deviceNo);
				log.setIpAddr(ipAddr);
				log.setMemberId(userId);
				log.setMemo("用户进行一键消警操作" );
				log.setOperatorType("6"); //操作类型 一键消警
				saveOperatorLog(log);
			}
		}
		return result;
	}

	@Override
	public CallResult defenceZones(String userId, String pwd,String deviceNo, String[] zoneNos, String istate, String ipAddr) {
		CallResult result = new CallResult();
		if (CheckUtil.isNullString(istate) || "1,2".indexOf(istate) == -1){ //布撤防状态不正确
			result.setCode("-3000");
			result.setDesc("布撤防状态不正确");
			return result;
		}
		boolean flag = queryDao.checkOwnManageDevice(userId, deviceNo); //是否是自己管理的防区
		if (!flag){
			result.setCode("-1000");
			result.setDesc("不是自己的防区不允许操作");  
		} else {
			//判断密码是否正确
			flag = queryDao.checkDeviceDefencePwd(deviceNo, pwd);
			if (!flag){
				result.setCode("-4000");
				result.setDesc("密码错误");  
				return result;
			}
			if (null != zoneNos && zoneNos.length>0){
				int num = 0;
				String zoneList = "";
				for (int i=0; i<zoneNos.length; i++){
					Zone zone = queryDao.queryZoneById(zoneNos[i]);
					if ("1,2,3".indexOf(zone.getZoneStyle()) != -1 || zone.getZoneOnline().equals("0")){
						logger.info("未上线的防区和24小时防区，布撤防不需要处理");
					} else {
						num ++;
						zoneList += zoneNos[i] + ",";
						//不更新状态
//						businessDao.changeZoneState(zone.getZoneNo(), istate);
					}
				}
				if (num == 0){
					result.setCode("-6000");
					result.setDesc("该设备里面的所有防区都是24h防区，本次操作无效");  
					return result;
				}
				
				zoneList = zoneList.substring(0, zoneList.length() -1);
				
				//把一键布撤防消息加入MQTT推送表
				Push push = new Push();
				push.setMsgId(Utils.getUUID());
				push.setAddDate(DateUtil.getNowDateTime());
				push.setPushService("0"); //MQTT推送
				push.setItype("5"); //消息类型为批量布撤防
				push.setZoneList(zoneList);
				push.setTopic(deviceNo); //topic 为设备编号
				businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getItype()); //删除无效批量布撤防推送消息
				String pushZoneState = "1"; //推送防区要进行的布撤防操作 默认要批量布防
				String operatorType = "7"; //默认批量布防
				if (istate.equals("1")){ //app为撤防中
					pushZoneState = "0";
					operatorType = "8"; //批量撤防
				}
				
				PushDto dto = new PushDto();
				BeanUtils.copyProperties(push, dto);
				dto.setDeviceNo(deviceNo);
				dto.setTime(System.currentTimeMillis() + "");
				dto.setCommandState(pushZoneState);
				push.setMsgText(Utils.wrapPushMsg(dto));
				logger.info("msgText:" + push.getMsgText());
				businessDao.savePushMsg(push);
				logger.info("批量布撤防操作成功，设备编号：" + deviceNo + ",防区编号：" + push.getZoneList() + ";处理后状态为：" + istate);
				
				//添加操作日志
				OperatorLog log = new OperatorLog();
				log.setDeviceNo(deviceNo);
				log.setIpAddr(ipAddr);
				log.setMemberId(userId);
				log.setMemo("用户进行" + (istate.equals("1")?"批量撤防":"批量布防") + "操作" );
				log.setOperatorType(operatorType); //操作类型
				saveOperatorLog(log);
			} else {
				result.setCode("-2000");
				result.setDesc("您的设备还没有防区，请先上传");  ;
			}
			
		}
		return result;
	}

	public static void main(String[] args) {
		Zone zone = new Zone();
		zone.setZoneStyle("");
		if("1,2,3".indexOf(zone.getZoneStyle()) !=-1){
			System.out.println("24小时防区不允许布撤防");
		} 
	}

	@Override
	@Transactional
	public CallResult editZoneStrainVpt(String userId, String zoneNo, String zoneStrainVpt, String ipAddr) {
		CallResult result = new CallResult();
		Zone zone = new Zone();
		zone.setZoneNo(zoneNo);
		zone.setUserId(userId);
		int count = queryDao.checkOwnZone(zone);
		if (count != 1){
			result.setCode("-1000");
			result.setDesc("该防区不属于您管理，您无权修改");
		} else {
			//通过mqtt修改防区阈值
//			businessDao.editZoneStrainVpt(zoneNo, zoneStrainVpt);
			//插入消息推送表
			
			Zone zoneDb = queryDao.queryZoneById(zoneNo);
			
			Push push = new Push();
			push.setZoneNo(zoneNo);
			push.setMsgId(Utils.getUUID());
			push.setAddDate(DateUtil.getNowDateTime());
			push.setPushService("0"); //MQTT推送
			push.setItype("6"); //消息类型为更新防区阈值
			push.setZoneStrainVpt(zoneStrainVpt);
			push.setTopic(zoneDb.getDeviceNo()); //topic 为设备编号
			businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getItype()); //删除无效批量布撤防推送消息
			String operatorType = "9"; //默认批量布防
			
			PushDto dto = new PushDto();
			BeanUtils.copyProperties(push, dto);
			dto.setDeviceNo(zoneDb.getDeviceNo());
			dto.setTime(System.currentTimeMillis() + "");
			push.setMsgText(Utils.wrapPushMsg(dto));
			logger.info("msgText:" + push.getMsgText());
			businessDao.savePushMsg(push);
			
			//添加操作日志
			OperatorLog log = new OperatorLog();
			log.setZoneNo(zoneNo);
			log.setDeviceNo(zoneDb.getDeviceNo());
			log.setIpAddr(ipAddr);
			log.setMemberId(userId);
			log.setMemo("用户进行更新防区张力阈值操作" );
			log.setOperatorType(operatorType); //操作类型
			saveOperatorLog(log);
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult pushConfig(String userId, int itype) {
		CallResult result = new CallResult();
		int count = queryDao.countPushConfig(userId);
		if (count != 0){
			businessDao.updatePushConfig(userId, itype);
		} else {
			businessDao.savePushConfig(userId, itype);
		}
		return result;
	}

	@Override
	public CallResult deviceHandleWaring(String userId, String deviceNo, String ipAddr) {
		CallResult result = new CallResult();
		boolean flag = queryDao.checkOwnManageDevice(userId, deviceNo);
		if (flag){
			Push push = new Push();
			push.setMsgId(Utils.getUUID());
			push.setAddDate(DateUtil.getNowDateTime());
			push.setPushService("0"); //MQTT推送
			push.setItype("4"); //消息类型为一键消警
			push.setTopic(deviceNo); //topic 为设备编号
			businessDao.deleteNotPushInvalidMsg(push.getTopic(), push.getItype()); //删除无效一键消警推送消息
			
			PushDto dto = new PushDto();
			BeanUtils.copyProperties(push, dto);
			dto.setDeviceNo(deviceNo);
			dto.setTime(System.currentTimeMillis() + "");
			push.setMsgText(Utils.wrapPushMsg(dto));
			logger.info("msgText:" + push.getMsgText());
			businessDao.savePushMsg(push);
			logger.info("一键消警操作中，设备编号：" + deviceNo);
			
			//添加操作日志
			OperatorLog log = new OperatorLog();
			log.setDeviceNo(deviceNo);
			log.setIpAddr(ipAddr);
			log.setMemberId(userId);
			log.setMemo("用户进行一键消警操作" );
			log.setOperatorType("6"); //操作类型 一键消警
			saveOperatorLog(log);
		} else {
			result.setCode("-1002");
			result.setDesc("您无权操作本设备");
		}
		
		return result;
		
	}
}
