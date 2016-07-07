package com.yunjing.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public abstract class BaseModel {
	private String appType = ""; //app类型0 安卓  1 苹果
	private String userId; //用户ID
	public String appId; //设备id
	public String appVersion; //app版本号
	public String token; //用户token
	public String xmAppId; //小米推送id
	public String ipAddr = ""; //ip地址
	private String phoneModel; //手机型号
	private String phoneSystem; //手机系统
	private String phoneBrand; //手机品牌
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getXmAppId() {
		return xmAppId;
	}
	public void setXmAppId(String xmAppId) {
		this.xmAppId = xmAppId;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	public String getPhoneSystem() {
		return phoneSystem;
	}
	public void setPhoneSystem(String phoneSystem) {
		this.phoneSystem = phoneSystem;
	}
	public String getPhoneBrand() {
		return phoneBrand;
	}
	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}

}
