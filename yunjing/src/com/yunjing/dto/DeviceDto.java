package com.yunjing.dto;

public class DeviceDto{
	private String deviceNo = ""; //设备编号
	private String deviceVersion = ""; //设备版本
	private String deviceUserName = ""; //设备用户名
	private String devicePwd = ""; //设备密码
	private String online = "";
	private String memberPwd = ""; //操作员密码
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getDeviceUserName() {
		return deviceUserName;
	}
	public void setDeviceUserName(String deviceUserName) {
		this.deviceUserName = deviceUserName;
	}
	public String getDevicePwd() {
		return devicePwd;
	}
	public void setDevicePwd(String devicePwd) {
		this.devicePwd = devicePwd;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	

}
