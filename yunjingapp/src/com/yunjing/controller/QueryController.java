package com.yunjing.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunjing.model.Device;
import com.yunjing.model.WarningInfo;
import com.yunjing.model.Zone;
import com.yunjing.service.PageService;
import com.yunjing.service.QueryService;
import com.yunjing.util.Pagination;
import com.yunjing.util.QueryResult;
import com.yunjing.util.Utils;

/**
 * 数据查询控制器
 * @author DONGYA
 *
 */
@Controller
public class QueryController {
	private static Logger logger = Logger.getLogger(QueryController.class);
	@Autowired
	private PageService pageService;
	
	@Autowired
	private QueryService queryService;
	
	
	/**
	 * 启动接口
	 * @param appType
	 * @param appVersion
	 * @return
	 */
	@RequestMapping(value="/start")
	public @ResponseBody Map<String,String> start(@RequestParam(value = "appType", required=false ) Integer appType, @RequestParam(value = "appVersion") String appVersion){
		return startApp(appType, appVersion);
	}

	private static Map<String,String> startApp(Integer appType, String appVersion) {
		Map<String,String> object = new HashMap<String,String>();
		try {
			logger.info("appType:" + appType + ";appVersion:" + appVersion);
			if (appType == null){
				appType = new Integer(0);
			}
			String fileName = Utils.ANDROID_UPGRADE;
			if (appType.intValue() == 1){
				fileName = Utils.IOS_UPGRADE;
			}
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(fileName));
			Element config = document.getRootElement();
			Element app = config.element("app");
			String anum = app.attributeValue("anum");
			
			if (Utils.getVersion(anum) > Utils.getVersion(appVersion)){ //有新版本
				object.put("code", "1");
				object.put("desc", "可以升级");
				object.put("appversion", anum);
				object.put("type", app.attributeValue("type"));
				object.put("content", app.attributeValue("content"));
				object.put("url", app.attributeValue("url"));
			} else {
				object.put("code", "0");
				object.put("desc", "无需升级");
			}
			
		} catch (Exception e) {
			object.put("code", "-1");
			object.put("desc", "解析升级文件异常");
			logger.error("解析升级文件异常");
			
		}
		return object;
	}

	@RequestMapping(value="/queryDevice")
	public @ResponseBody QueryResult queryDevice(@RequestParam(value="deviceNo") String deviceNo){
		QueryResult result = new QueryResult();
		try {
			List<Device> devices = new ArrayList<Device>();
			Device device =  queryService.queryDeviceById(deviceNo);
			devices.add(device);
			result.setList(devices);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
		
	}
	
	@RequestMapping(value="/queryZone")
	public @ResponseBody QueryResult queryZone(@RequestParam(value="zoneNo") String zoneNo){
		
		QueryResult result = new QueryResult();
		try {
			List<Zone> zones = new ArrayList<Zone>();
			Zone zone =  queryService.queryZoneById(zoneNo);
			zones.add(zone);
			result.setList(zones);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	
	@RequestMapping(value="/queryUserDevices")
	public @ResponseBody QueryResult queryUserDevices(@RequestParam(value="userId") String userId){
		QueryResult result = new QueryResult();
		try {
			List<?> devices = queryService.queryUserDevices(userId);
			result.setList(devices);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	@RequestMapping(value="/queryWarnings")
	public @ResponseBody QueryResult queryWarnings(@RequestParam(value="istate", required=false) Integer istate, @RequestParam(value="userId") String userId){
		QueryResult result = new QueryResult();
		try {
			List<?> warnings = queryService.queryWarningInfo(istate, userId);
			WarningInfo info = (WarningInfo)warnings.get(0);
			System.out.println("warnTypeName:" + info.getWarnTypeName() + ";warnType:" + info.getWarnType());
			result.setList(warnings);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	
	@RequestMapping(value="/queryUserCollect")
	public @ResponseBody QueryResult queryWarnings(@RequestParam(value="userId") String userId){
		QueryResult result = new QueryResult();
		try {
			List<?> collects = queryService.queryUserCollects(userId);
			result.setList(collects);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	@RequestMapping(value="/queryWarningsPage")
	public @ResponseBody QueryResult queryWarningsPage(@RequestParam(value="userId", required=false) String userId,@RequestParam(value="istate", required=false) Integer istate, @RequestParam(value="pn") int pn){
		QueryResult result = new QueryResult();
		try {
			Pagination page = queryService.queryWarningInfo(userId, istate, pn);
			result.setPage(page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	
	/**
	 * 查询设备的防区列表
	 * @param deviceNo
	 * @return
	 */
	@RequestMapping(value="/queryDeviceZones")
	public @ResponseBody QueryResult queryDeviceZones(@RequestParam(value="deviceNo") String deviceNo){
		QueryResult result = new QueryResult();
		try {
			List<?> collects = queryService.queryDeviceZones(deviceNo);
			result.setList(collects);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-1000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	
}
