package com.yunjing.model;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.yunjing.util.DateUtil;

public class Zone extends BaseModel{
	
	private String zoneNo = ""; //防区编号
	private String zoneName = ""; //防区名称
	private String zoneDesc = ""; //防区描述
	private String zoneContactor = ""; //防区联系人
	private String zonePhone = ""; //防区联系人
	private String zoneLoc = ""; //地理位置
	private String zoneType = ""; //防区类型 1脉冲电子围栏 2触网脉冲电子围栏 3张力围栏 4泄露电缆 5振动光纤 6地址码
	private String zoneVmp = ""; //电压值
	private String zoneStrain = ""; //张力值
	private String zoneStrainVpt = ""; //阈值
	private String zoneOnline = ""; //0未上线  1已上线
	
	private String zoneState = ""; //布撤防状态    1 撤防中  2布防中  3已撤防  4已布防  
	private Timestamp addDate; //添加时间
	private String operator; //操作人
	private String deviceNo; //设备编号
	private String zoneStyle = ""; //1：屏蔽防区  2：24小时有声防区 3：24小时无声防区 4：即时防区  5：布撤防防区  6：延时防区  7：传递延时防区 (若不传或则为空则以即时防区处理)
	
	private String userType;
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	private String deviceName; //设备名称
	
	public String getZoneNo() {
		return zoneNo;
	}
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getZoneDesc() {
		return zoneDesc;
	}
	public void setZoneDesc(String zoneDesc) {
		this.zoneDesc = zoneDesc;
	}
	public String getZoneContactor() {
		return zoneContactor;
	}
	public void setZoneContactor(String zoneContactor) {
		this.zoneContactor = zoneContactor;
	}
	public String getZonePhone() {
		return zonePhone;
	}
	public void setZonePhone(String zonePhone) {
		this.zonePhone = zonePhone;
	}
	public String getZoneLoc() {
		return zoneLoc;
	}
	public void setZoneLoc(String zoneLoc) {
		this.zoneLoc = zoneLoc;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}

	public String getAddTime(){
		return DateUtil.Timestamp2String(getAddDate());
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
	public String getZoneType() {
		return zoneType;
	}
	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
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
	public String getZoneState() {
		return zoneState;
	}
	public void setZoneState(String zoneState) {
		this.zoneState = zoneState;
	}
	public String getZoneOnline() {
		return zoneOnline;
	}
	public void setZoneOnline(String zoneOnline) {
		this.zoneOnline = zoneOnline;
	}
	public String getZoneStyle() {
		return zoneStyle;
	}
	public void setZoneStyle(String zoneStyle) {
		this.zoneStyle = zoneStyle;
	}
	
	
}
