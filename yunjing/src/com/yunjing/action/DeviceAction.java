package com.yunjing.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.DeviceDto;
import com.yunjing.entity.Member;
import com.yunjing.entity.Zone;
import com.yunjing.service.DeviceService;
import com.yunjing.service.MemberService;
import com.yunjing.service.ZoneService;
import com.yunjing.util.CallResult;
import com.yunjing.util.ExcelUtils;

public class DeviceAction extends BaseAction{
	private DeviceService deviceService;
	private List<?> devices;
	private DeviceDto device;
	private ZoneService zoneService;
	private List<Zone> zones;
	private String deviceNo;
	private String deviceName;
	private MemberService memberService;
	private List<Member> members;
	
	/**
	 * 查询所有设备
	 * @return
	 */
	public String index(){
		try {
			devices = deviceService.getAllPaging(device);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	/**
	 * 查询设备
	 * @return
	 */
	public String query(){
		try {
			devices = deviceService.getAllPaging(device);
			return "search";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	
	/**
	 * 查询设备下的通道
	 * @return
	 */
	public String queryZones(){
		try {
			deviceName = URLDecoder.decode(deviceName,"UTF-8");
			System.out.println("deviceName:" + deviceName);
			zones = zoneService.getZonesByDevice(deviceNo);
			return "deviceZones";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 查询设备的用户
	 * @return
	 */
	public String queryUsers(){
		try {
			deviceName = URLDecoder.decode(deviceName,"UTF-8");
			System.out.println("deviceName:" + deviceName);
			members = memberService.getByDeviceNo(deviceNo);
			return "deviceUsers";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public void exportExcel(){
		CallResult result = new CallResult();
		try {
			devices = deviceService.getAll(device);
			if (null != devices && devices.size() > 0){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Connection", "close");  
			    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8"); 
				OutputStream out = response.getOutputStream();
				String excelName = "设备信息报表";  
				//转码防止乱码  
				response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
				ExcelUtils.exportExcel(excelName, devices, out, 1);
				out.close();
			} else {
				result.setDesc("暂时");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("导出excel失败，系统错误");
		}  
		
	}
	
	
	public DeviceDto getDevice() {
		return device;
	}
	public void setDevice(DeviceDto device) {
		this.device = device;
	}
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}


	public List<?> getDevices() {
		return devices;
	}


	public void setDevices(List<?> devices) {
		this.devices = devices;
	}


	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public void setZoneService(ZoneService zoneService) {
		this.zoneService = zoneService;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
}
