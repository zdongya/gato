package com.yunjing.entity;
import java.sql.Timestamp;

import com.yunjing.util.CheckUtil;
/**
 * 设备表
 * @author DONGYA
 *
 */
public class Device{
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
	private Timestamp updateDate; //最后登记时间
	private String online; //设备在线状态  0掉线 1在线
	
	private String groupId; //组编号
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	
	
	public String getOnlineState(){
		if (CheckUtil.isNullString(online)){
			return "状态未知";
		} else {
			if ("0".equals(online)){
				return "掉线";
			} else {
				return "上线";
			}
		}
	}
}
