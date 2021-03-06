package com.yunjing.model;

import java.sql.Timestamp;

import com.yunjing.util.CheckUtil;
import com.yunjing.util.DateUtil;

public class Device extends BaseModel{
	
	private String deviceNo;
	private String deviceName;
	private String deviceUserName;
	private String devicePwd;
	
	private String deviceLocal;
	private String version;
	private String contactPerson;
	private String cellphone;
	private String address;
	private Timestamp addDate;
	private String groupId;
	
	private String collectId; 
	
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
	public String getDeviceLocal() {
		return deviceLocal;
	}
	public void setDeviceLocal(String deviceLocal) {
		this.deviceLocal = deviceLocal;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	
	public String getAddTime(){
		return DateUtil.Timestamp2String(getAddDate());
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCollectState() {
		if (CheckUtil.isNullString(getCollectId())){
			return "0";
		} else {
			return "1";
		}
	}
	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

}
