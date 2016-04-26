package com.yunjing.dto;

import java.util.ArrayList;
import java.util.List;

public class UploadZone {
	private String deviceNo = ""; //设备编号
	private List<?> zoneList = new ArrayList();
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public List<?> getZoneList() {
		return zoneList;
	}
	public void setZoneList(List<?> zoneList) {
		this.zoneList = zoneList;
	}
	
}
