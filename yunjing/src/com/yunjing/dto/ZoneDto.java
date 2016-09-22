package com.yunjing.dto;

import com.yunjing.util.CheckUtil;

public class ZoneDto {
	private String zoneNo = ""; //防区编号
	private String zoneName = "";
	private String zoneType = ""; //1脉冲电子围栏 2触网脉冲电子围栏 3张力围栏 4泄露电缆 5振动光纤 6地址码
	private String zoneOnline = ""; //0未上线  1已上线
	private String zoneState = ""; //布撤防状态   空为未上传   1 撤防中  2布防中  3已撤防  4已布防  
	private String zoneVmp = ""; //电压值
	private String zoneStrain = ""; //张力值
	private String zoneStrainVpt = ""; //张力阈值
	private String uploadType = ""; //0添加  1删除  2上报通道布撤防状态
	private String zoneStyle = "4"; // 1：屏蔽防区  2：24小时有声防区 3：24小时无声防区 4：即时防区  5：布撤防防区  6：延时防区  7：传递延时防区 (若不传或则为空则以即时防区处理)
	private String voltlev = ""; //电压等级 0则不显示
	private String senslev = ""; //灵敏度 0则不显示
	private String workmod = ""; //工作模式 0则不显示
	private String zoneParam = ""; //脉冲防区参数设置  电压,灵敏度,工作模式
	public String getZoneParam() {
		return zoneParam;
	}
	public void setZoneParam(String zoneParam) {
		if (!CheckUtil.isNullString(zoneParam)){
			String[] zoneParams = zoneParam.split(",");
			if (zoneParams.length == 3){
				setVoltlev(zoneParams[0]);
				setSenslev(zoneParams[1]);
				setWorkmod(zoneParams[2]);
			}
		}
		
		this.zoneParam = zoneParam;
	}
	public String getVoltlev() {
		if (voltlev.equals("0")){
			voltlev = "";
		}
		return voltlev;
	}
	public void setVoltlev(String voltlev) {
		this.voltlev = voltlev;
	}
	public String getSenslev() {
		if (senslev.equals("0")){
			senslev = "";
		}
		return senslev;
	}
	public void setSenslev(String senslev) {
		this.senslev = senslev;
	}
	public String getWorkmod() {
		if (workmod.equals("0")){
			workmod = "";
		}
		return workmod;
	}
	public void setWorkmod(String workmod) {
		this.workmod = workmod;
	}
	public String getZoneStyle() {
		return zoneStyle;
	}
	public void setZoneStyle(String zoneStyle) {
		this.zoneStyle = zoneStyle;
	}
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
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
}
