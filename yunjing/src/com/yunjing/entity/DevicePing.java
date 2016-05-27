package com.yunjing.entity;

public class DevicePing {
	private String deviceNo;
	private String ipAddress;
	private int ccount;
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public DevicePing(String deviceNo, String ipAddress) {
		super();
		this.deviceNo = deviceNo;
		this.ipAddress = ipAddress;
	}
	public int getCcount() {
		return ccount;
	}
	public void setCcount(int ccount) {
		this.ccount = ccount;
	}

}
