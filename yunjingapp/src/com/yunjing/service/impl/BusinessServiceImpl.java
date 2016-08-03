package com.yunjing.service.impl;

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
					businessDao.hanleWarnings(warningInfo);
					Zone zoneDb = queryDao.queryZoneById(warningInfo.getZoneNo());
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
		if (CheckUtil.isNullString(device.getDeviceNo()) || CheckUtil.isNullString(device.getDevicePwd())){
			result.setCode("-1000");
			result.setDesc("参数错误。");
		} else {
			if (queryDao.queryUserBindDeviceFlag(device.getDeviceNo(), device.getUserId())){
				result.setCode("-2000");
				result.setDesc("您已经添加过本设备不能重复添加。");
			} else {
				boolean deviceExistFlag = queryDao.checkDeviceByNo(device.getDeviceNo());
				if (!deviceExistFlag){ //设备不存在
					result.setCode("-3000");
					result.setDesc("设备不存在");
				} else {
					boolean bindCheck = queryDao.bindDeviceCheck(device); //信息正确
					if (bindCheck){
						String itype = device.getUserType();
						if (CheckUtil.isNullString(itype)){
							itype = "0";
						}
						if (queryDao.haveBindDataCheck(device.getUserId(), device.getDeviceNo())){ //曾经绑定过
							businessDao.bindDevice(device.getUserId(), device.getDeviceNo(), Integer.parseInt(itype));
						} else { //未绑定过
							businessDao.saveUserDevice(device.getUserId(), device.getDeviceNo(), Integer.parseInt(itype));
						}
						if (!CheckUtil.isNullString(device.getDeviceName())){
							businessDao.editDeviceName(device.getDeviceNo(), device.getDeviceName());
						}
						result.setDesc("绑定设备成功");
					} else {
						result.setCode("-4000");
						result.setDesc("设备信息不正确");
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
				if (CheckUtil.isNullString(zoneDb.getZoneState()) || "1,2".indexOf(zoneDb.getZoneState())!=-1){
					result.setCode("-4000");
					result.setDesc("不允许修改该防区的布撤防状态");
				} else {
					if (istate.equals("1")&& !zoneDb.getZoneState().equals("4")){
						result.setCode("-5000");
						result.setDesc("该防区的状态为已撤防状态不能改为撤防中");
					} 
					if (istate.equals("2")&& !zoneDb.getZoneState().equals("3")){
						result.setCode("-6000");
						result.setDesc("该防区的状态为已布防状态不能改为布防中");
					}
				}
			}
			
			if (result.getCode().equals("10000")){ //正确
				businessDao.changeZoneState(zoneNo, istate);
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

}
