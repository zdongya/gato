package com.yunjing.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunjing.model.Device;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.service.BusinessService;
import com.yunjing.util.CallResult;

/**
 * 业务控制器
 * @author DONGYA
 */
@Controller
public class ServiceController {
	private static Logger logger = Logger.getLogger(ServiceController.class);
	
	@Autowired
	private BusinessService businessService;
	@RequestMapping(value="/service/handleWaring")
	public @ResponseBody CallResult handleWaring(HttpSession session, @ModelAttribute WarningInfo warningInfo){
		CallResult callResult = new CallResult();
		try {
			
			String ipAddr = (String)session.getAttribute("visitIp");
			warningInfo.setIpAddr(ipAddr);
			callResult = businessService.handleWarnings(warningInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	/**
	 * 添加收藏
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/addCollectsLogin")
	public @ResponseBody CallResult addCollects(@RequestParam(value="deviceNo") String deviceNo, @RequestParam(value="userId") String userId){
		CallResult callResult = new CallResult();
		try {
			callResult = businessService.addCollects(userId, deviceNo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	@RequestMapping(value="/service/bindDevice")
	public @ResponseBody CallResult addDevice(@ModelAttribute Device device){
		CallResult callResult = new CallResult();
		try {
			logger.info("绑定设备----------------------------------->device---:" + device);
			callResult = businessService.bindDevice(device);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	/**取消收藏
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/cancelCollects")
	public @ResponseBody CallResult cancelCollects(@RequestParam(value="collectId") String collectId, @RequestParam(value="deviceNo") String deviceNo, @RequestParam(value="userId") String userId){
		CallResult callResult = new CallResult();
		try {
			callResult = businessService.cancelCollects(collectId, userId, deviceNo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	/**
	 * 编辑防区
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/editZone")
	public @ResponseBody CallResult editZone(@ModelAttribute Zone zone){
		CallResult callResult = new CallResult();
		try {
			callResult = businessService.editZone(zone);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	/**
	 * 添加反馈
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/addRetroaction")
	public @ResponseBody CallResult addRetroaction(@RequestParam(value="contents") String contents, @RequestParam(value="userId") String userId,@RequestParam(value="contact", required=false) String contact){
		CallResult callResult = new CallResult();
		try {
			callResult = businessService.addRetroaction(userId, contents, contact);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	/**
	 * 布撤防
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/defence")
	public @ResponseBody CallResult defence(HttpSession session, @RequestParam(value="userId") String userId,@RequestParam(value="zoneNo") String zoneNo,@RequestParam(value="istate") String istate){
		CallResult callResult = new CallResult();
		try {
			String ipAddr = (String)session.getAttribute("visitIp");
			callResult = businessService.defence(userId, zoneNo, istate, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	@RequestMapping(value="/service/unbindDevice")
	public @ResponseBody CallResult unbindDevice(@RequestParam(value="userId") String userId, @RequestParam(value="deviceNo") String deviceNo){
		CallResult callResult = new CallResult();
		try {
			callResult = businessService.unbindDevice(userId, deviceNo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	@RequestMapping(value="/service/editDeviceName")
	public @ResponseBody CallResult editDeviceName(@RequestParam(value="userId") String userId,@RequestParam(value="deviceNo") String deviceNo, @RequestParam(value="deviceName") String deviceName){
		CallResult callResult = new CallResult();
		try {
			logger.info("------------------------------->修改设备名称，deviceName---:" + deviceName);
			callResult = businessService.editDeviceName(userId, deviceNo, deviceName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	@RequestMapping(value="/service/testLogin")
	public @ResponseBody CallResult testIp(HttpSession session){
		String ipAddr = (String)session.getAttribute("visitIp");
		logger.info("controller session visitIp:" + ipAddr);
		CallResult callResult = new CallResult();
		return callResult;
		
	}
	
	
	/**
	 * 设备一键布撤防
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/deviceDefence")
	public @ResponseBody CallResult defenceDevice(HttpSession session, @RequestParam(value="userId") String userId,@RequestParam(value="deviceNo") String deviceNo,
			@RequestParam(value="istate") String istate,@RequestParam(value="pwd") String pwd){
		CallResult callResult = new CallResult();
		try {
			String ipAddr = (String)session.getAttribute("visitIp");
			callResult = businessService.deviceDefence(userId, pwd, deviceNo, istate, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	/**
	 * 消除报警接口(用户所有在线的设备进行消警)
	 * @param session
	 * @param userId
	 * @param deviceNo
	 * @return
	 */
	@RequestMapping(value="/service/HandleAllWaring")
	public @ResponseBody CallResult HandleAllWaring(HttpSession session, @RequestParam(value="userId") String userId){
		CallResult callResult = new CallResult();
		try {
			
			String ipAddr = (String)session.getAttribute("visitIp");
			callResult = businessService.deviceHandleWaring(userId, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	/**
	 * 批量布撤防
	 * @param deviceNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/batchDefence")
	public @ResponseBody CallResult defenceZones(HttpSession session, @RequestParam(value="userId") String userId,@RequestParam(value="deviceNo") String deviceNo,
			@RequestParam(value="istate") String istate,@RequestParam(value="pwd") String pwd, @RequestParam(value="zoneNos") String zoneNos){
		CallResult callResult = new CallResult();
		try {
			String ipAddr = (String)session.getAttribute("visitIp");
			String[] zoneStr = zoneNos.split(",");
			callResult = businessService.defenceZones(userId, pwd,deviceNo, zoneStr, istate, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	

	/**
	 * 更新防区的阈值
	 * @param zoneNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/editZoneStrainVpt")
	public @ResponseBody CallResult editZoneStrainVpt(HttpSession session, @RequestParam(value="userId") String userId, @RequestParam(value="zoneNo") String zoneNo, @RequestParam(value="zoneStrainVpt") String zoneStrainVpt){
		CallResult callResult = new CallResult();
		try {
			String ipAddr = (String)session.getAttribute("visitIp");
			callResult = businessService.editZoneStrainVpt(userId, zoneNo, zoneStrainVpt, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	/**
	 * 客户端推送设置
	 * @param userId
	 * @param itype   0不推送  1只在白天推送  2所有时间段推送
	 * @return
	 */
	@RequestMapping(value="/service/pushConfig")
	public @ResponseBody CallResult editZoneStrainVpt(@RequestParam(value="userId") String userId, @RequestParam(value="itype") int itype){
		CallResult callResult = new CallResult();
		try {
			callResult = businessService.pushConfig(userId, itype);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	
	/**
	 * 一键消警接口(对选中设备的所有防区进行消警)
	 * @param session
	 * @param userId
	 * @param deviceNo
	 * @return
	 */
	@RequestMapping(value="/service/deviceHandleWaring")
	public @ResponseBody CallResult deviceHandleWaring(HttpSession session, @RequestParam(value="userId") String userId,@RequestParam(value="deviceNo") String deviceNo){
		CallResult callResult = new CallResult();
		try {
			
			String ipAddr = (String)session.getAttribute("visitIp");
			callResult = businessService.deviceHandleWaring(userId, deviceNo, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	/**
	 * 更新脉冲防区的参数
	 * @param zoneNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/service/editZoneParam")
	public @ResponseBody CallResult editZoneParam(HttpSession session, @RequestParam(value="userId") String userId, @RequestParam(value="zoneNo") String zoneNo, @RequestParam(value="zoneParam") String zoneParam){
		CallResult callResult = new CallResult();
		try {
			int length = zoneParam.split(",").length;
			if (length == 3){
				String ipAddr = (String)session.getAttribute("visitIp");
				callResult = businessService.editZoneParam(userId, zoneNo, zoneParam, ipAddr);
			} else {
				callResult.setCode("-1001");
				callResult.setDesc("防区参数有误");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
	
	/**
	 * 批处理报警
	 * @param session
	 * @param userId
	 * @param deviceNo
	 * @return
	 */
	@RequestMapping(value="/service/batchHandleWaring")
	public @ResponseBody CallResult batchHandleWaring(HttpSession session, @RequestParam(value="userId") String userId,@RequestParam(value="warnIds") String warnIds,@RequestParam(value="istate") int istate, @RequestParam(value="memo") String memo){
		CallResult callResult = new CallResult();
		try {
			String ipAddr = (String)session.getAttribute("visitIp");
			callResult = businessService.batchHandleWaring(userId, warnIds, istate, memo, ipAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			callResult.setCode("-10000");
			callResult.setDesc("系统异常");
		}
		return callResult;
	}
	
}
