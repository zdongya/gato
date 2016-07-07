package com.lanyuan.controller.business;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.controller.index.BaseController;
import com.lanyuan.entity.DeviceFormMap;
import com.lanyuan.entity.SysUserFormMap;
import com.lanyuan.mapper.BusinessMapper;
import com.lanyuan.plugin.PageView;
import com.lanyuan.util.Common;
/**
 * 
 * @author DONGYA
 *
 */
@Controller
@RequestMapping("/device/")
public class DeviceController extends BaseController {
	
	@Inject
	private BusinessMapper businessMapper;
		
	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/business/device/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(String pageNow,String pageSize,String column,String sort) throws Exception {
		DeviceFormMap deviceFormMap = getFormMap(DeviceFormMap.class);
		deviceFormMap=toFormMap(deviceFormMap, pageNow, pageSize, "");
		deviceFormMap.put("column", "addDate");
		deviceFormMap.put("sort", "desc");
        pageView.setRecords(businessMapper.findDevicePage(deviceFormMap));
        return pageView;
	}
	
	
	@ResponseBody
	@RequestMapping("deviceUsers")
	public List<SysUserFormMap> reslists(Model model) throws Exception {
		SysUserFormMap sysUserFormMap = getFormMap(SysUserFormMap.class);
		sysUserFormMap.put("column", "registerdate");
		sysUserFormMap.put("sort", "desc");
		List<SysUserFormMap> mps = businessMapper.findDeviceUsers(sysUserFormMap);
		return mps;
	}

}