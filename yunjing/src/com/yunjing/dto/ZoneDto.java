package com.yunjing.dto;

public class ZoneDto {
	private String zoneNo = ""; //防区编号
	private String zoneType = ""; //1脉冲电子围栏 2触网脉冲电子围栏 3张力围栏 4泄露电缆 5振动光纤 6地址码
	private String zoneOnline = ""; //0未上线  1已上线
	private String zoneState = ""; //布撤防状态   空为未上传   1 撤防中  2布防中  3已撤防  4已布防  
	private String zoneVmp = ""; //电压值
	private String zoneStrain = ""; //张力值
	private String zoneStrainVpt = ""; //张力阈值
	private String uploadType = ""; //0添加  1删除  2上报通道布撤防状态
	
	private String deviceNo = ""; //设备编号
	public String getZoneNo() {
		return zoneNo;
	}
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	public String getZoneType() {
		return zoneType;
	}
	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}
	public String getZoneOnline() {
		return zoneOnline;
	}
	public void setZoneOnline(String zoneOnline) {
		this.zoneOnline = zoneOnline;
	}
	public String getZoneState() {
		return zoneState;
	}
	public void setZoneState(String zoneState) {
		this.zoneState = zoneState;
	}
	public String getZoneVmp() {
		return zoneVmp;
	}
	public void setZoneVmp(String zoneVmp) {
		this.zoneVmp = zoneVmp;
	}
	public String getZoneStrain() {
		return zoneStrain;
	}
	public void setZoneStrain(String zoneStrain) {
		this.zoneStrain = zoneStrain;
	}
	public String getZoneStrainVpt() {
		return zoneStrainVpt;
	}
	public void setZoneStrainVpt(String zoneStrainVpt) {
		this.zoneStrainVpt = zoneStrainVpt;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
}
