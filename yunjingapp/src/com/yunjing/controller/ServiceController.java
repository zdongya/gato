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
}
