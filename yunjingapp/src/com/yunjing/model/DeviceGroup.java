package com.yunjing.model;

import java.sql.Timestamp;
import java.util.List;

import com.yunjing.util.DateUtil;

public class DeviceGroup extends BaseModel{
	
	private String groupId;
	private String groupName;
	private Integer istate;
	private String operator;
	private Timestamp cadddate;
	private List<Device> devices;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getIstate() {
		return istate;
	}
	public void setIstate(Integer istate) {
		this.istate = istate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getCadddate() {
		return cadddate;
	}
	public void setCadddate(Timestamp cadddate) {
		this.cadddate = cadddate;
	}
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public String getAddTime(){
		return DateUtil.Timestamp2String(getCadddate());
	}
	

}
