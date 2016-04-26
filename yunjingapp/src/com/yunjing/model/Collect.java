package com.yunjing.model;

import java.sql.Timestamp;

import com.yunjing.util.DateUtil;

/**
 * 收藏表
 * @author DONGYA
 *
 */
public class Collect extends BaseModel{
	private String collectId;
	private String deviceNo;
	private Timestamp addDate;
	private String deviceName;
	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getAddTime(){
		return DateUtil.Timestamp2String(getAddDate());
	}

}
