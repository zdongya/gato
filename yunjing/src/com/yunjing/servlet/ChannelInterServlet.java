package com.yunjing.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.yunjing.dto.DeviceDto;
import com.yunjing.dto.UploadZone;
import com.yunjing.entity.DevicePing;
import com.yunjing.entity.WarningInfo;
import com.yunjing.entity.Zone;
import com.yunjing.service.ChannelInterService;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.JsonUtil;
import com.yunjing.util.Utils;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * 通道接口servlet
 * @author DONGYA
 *
 */
@SuppressWarnings("serial")
public class ChannelInterServlet extends HttpServlet{
	private static Logger logger = Logger.getLogger(ChannelInterServlet.class);
	private ChannelInterService channelInterService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CallResult callResult = new CallResult();
		callResult.setCode("-20000");
		callResult.setDesc("不支持GET方式请求");
		writerJsonResult(resp, callResult);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		channelInterService = new ChannelInterService();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CallResult result = checkRequest(req);
		if (result.getCode().equals("0000")){ //合法请求
			String action = req.getParameter("action");
			if (action.equals("activeDevice")){ //激活设备
				activeDevice(req, resp);
			} else if (action.equals("uploadZone")){ //上传通道信息接口
				uploadZoneInfo(req, resp);
			} else if(action.equals("alarm")){ //报警接口
				alarmToServer(req, resp);
			} else if (action.equals("ping")){ //心跳接口
				devicePing(req, resp);
			} else {
				result.setCode("-10000");
				result.setDesc("未知的业务");
				writerJsonResult(resp, result);
			}
		} else {
			writerJsonResult(resp, result);
		}
		
	}
	
	private void uploadZoneInfo(HttpServletRequest req, HttpServletResponse resp) {
		CallResult result = new CallResult();
		try {
			String deviceNo = req.getParameter("deviceNo");
			String bInit = req.getParameter("bInit");
			String zoneList = req.getParameter("zoneList");
			if (CheckUtil.isNullString(deviceNo)){
				result.setCode("-6666");
				result.setDesc("所属设备编号不能为空");
				return ;
			}
			if (CheckUtil.isNullString(zoneList)){
				result.setCode("-6666");
				result.setDesc("防区列表不能为空");
				return ;
			}
			if (CheckUtil.isNullString(bInit)){
				bInit = "0";
			}
			
			List<?> list = JsonUtil.jsonArray2List(zoneList, Class.forName("com.yunjing.dto.ZoneDto"));
			UploadZone uploadZone = new UploadZone();
			uploadZone.setDeviceNo(deviceNo);
			uploadZone.setZoneList(list);
			uploadZone.setbInit(bInit);
			result = channelInterService.updateZoneInfo(uploadZone);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (result.getCode().equals("0000")){
				result.setCode("-9999");
				result.setDesc("请求异常");
			}
		} finally {
			writerJsonResult(resp, result);
		}
	}


	//报警接口
	private void alarmToServer(HttpServletRequest req, HttpServletResponse resp) {
		CallResult result = new CallResult();
		try {
			String warningId = req.getParameter("warningId");
			String zoneNo = req.getParameter("zoneNo");
			String warnDate = req.getParameter("warnDate");
			String warnType = req.getParameter("warnType"); //报警类型
			if (CheckUtil.isNullString(warningId)){
				result.setCode("-6666");
				result.setDesc("报警编号不能为空");
				return ;
			}
			if (CheckUtil.isNullString(zoneNo)){
				result.setCode("-6666");
				result.setDesc("防区编号不能为空");
				return ;
			}
			if (CheckUtil.isNullString(warnDate)){
				result.setCode("-6666");
				result.setDesc("报警日期不能为空");
				return ;
			}
			if (CheckUtil.isNullString(warnType)){
				result.setCode("-6666");
				result.setDesc("报警类型不能为空");
				return ;
			}
			WarningInfo info = new WarningInfo();
			info.setWarningId(warningId);
			info.setWarnDate(warnDate);
			info.setZoneNo(zoneNo);
			info.setWarnType(warnType);
			result = channelInterService.saveWarningInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
			if (result.getCode().equals("0000")){
				result.setCode("-9999");
				result.setDesc("请求异常");
			}
		} finally {
			writerJsonResult(resp, result);
		}
		
	}

	private CallResult checkRequest(HttpServletRequest req) {
		CallResult result = new CallResult();
		if (CheckUtil.isNullString(req.getParameter("action")) || CheckUtil.isNullString(req.getParameter("time")) || CheckUtil.isNullString(req.getParameter("sign"))){
			result.setCode("-6666");
			result.setDesc("缺少参数");
		} else {
			Enumeration<String> parms = req.getParameterNames();
			String sign = req.getParameter("sign");
			logger.info("clientSign:" + sign);
			Map<String, String> map = new HashMap<String, String>();
			while(parms.hasMoreElements()){
				String name = parms.nextElement();
				String value = req.getParameter(name);
				if (!name.equals("sign")){
					map.put(name, value);
				}
				logger.info(name + ":" + value);
			}
			String toSignStr = Utils.getSignStr(map, Utils.KEY);
			logger.info("tosignStr:" + toSignStr);
			String serverSign = Utils.getMD5Str(toSignStr);
			logger.info("serverSign:" + serverSign);
			if (serverSign.equals(sign)){
				logger.info("签名正确，合法请求");
			} else {
				logger.info("签名错误，非法请求");
				result.setCode("-9999");
				result.setDesc("签名错误，非法请求");
			}
		}
		return result;
	}

	/**
	 * 激活设备
	 * @param req
	 * @param resp
	 */
	private void activeDevice(HttpServletRequest req, HttpServletResponse resp){
		CallResult result = new CallResult();
		try {
			String deviceNo = req.getParameter("deviceNo");
			String deviceUserName = req.getParameter("deviceUserName");
			String devicePwd = req.getParameter("devicePwd");
			String deviceVersion = req.getParameter("deviceVersion");
			if (CheckUtil.isNullString(deviceNo)){
				result.setCode("-6666");
				result.setDesc("设备编号不能为空");
				return ;
			}
			
			DeviceDto dto = new DeviceDto();
			dto.setDeviceNo(deviceNo);
			dto.setDeviceVersion(deviceVersion);
			dto.setDeviceUserName(deviceUserName);
			dto.setDevicePwd(devicePwd);
			result = channelInterService.deviceActive(dto);
		} catch (Exception e) {
			e.printStackTrace();
			if (result.getCode().equals("0000")){
				result.setCode("-9999");
				result.setDesc("请求异常");
			}
		} finally {
			writerJsonResult(resp, result);
		}
	}
	
	/**
	 * 输出json结果字符串
	 * @param resp
	 * @param callResult
	 */
	private void writerJsonResult(HttpServletResponse resp, CallResult callResult){
		try {
			resp.setCharacterEncoding("UTF-8");  
			resp.setContentType("application/json; charset=utf-8");
			Writer writer = resp.getWriter();
			writer.write(JSON.toJSONString(callResult));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设备心跳
	 * @param req
	 * @param resp
	 */
	private void devicePing(HttpServletRequest req, HttpServletResponse resp){
		CallResult result = new CallResult();
		try {
			String deviceNo = req.getParameter("deviceNo");
			if (CheckUtil.isNullString(deviceNo)){
				result.setCode("-6666");
				result.setDesc("设备编号不能为空");
				return ;
			}
			String ipAddr = getIpAddr(req);
			DevicePing devicePing = new DevicePing(deviceNo, ipAddr);
			result = channelInterService.devicePing(devicePing);
		} catch (Exception e) {
			e.printStackTrace();
			if (result.getCode().equals("0000")){
				result.setCode("-9999");
				result.setDesc("请求异常");
			}
		} finally {
			writerJsonResult(resp, result);
		}
	}
	
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("Proxy-Client-IP");     
	     }     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("WL-Proxy-Client-IP");     
	      }     
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	          ip = request.getRemoteAddr();     
	     }     
	     return ip;    
	}

}
