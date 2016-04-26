package com.yunjing.model;

import java.sql.Timestamp;

import com.yunjing.util.DateUtil;

/**
 * 报警信息
 * @author DONGYA
 *
 */
public class WarningInfo extends BaseModel{
	private String warningId;
	private String zoneNo;
	private Timestamp warnDate;
	private Integer istate; //0 未解决  1已解决  2误报
	private String handler;
	private Timestamp handleDate;
	private String memo;
	private String zoneName;
	private String deviceName;
	private String zoneContactor;
	private String zonePhone;
	private String warnType = ""; //报警类型
	
	
	private Zone zone;
	
	public String getWarningId() {
		return warningId;
	}
	public void setWarningId(String warningId) {
		this.warningId = warningId;
	}
	public String getZoneNo() {
		return zoneNo;
	}
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	public Timestamp getWarnDate() {
		return warnDate;
	}
	public void setWarnDate(Timestamp warnDate) {
		this.warnDate = warnDate;
	}
	public Integer getIstate() {
		return istate;
	}
	public void setIstate(Integer istate) {
		this.istate = istate;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public Timestamp getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(Timestamp handleDate) {
		this.handleDate = handleDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	private String getWarnTime(){
		return DateUtil.Timestamp2String(getWarnDate());
	}
	private String getHandleTime(){
		return DateUtil.Timestamp2String(getHandleDate());
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
	public String getWarnTypeName(){
		if ("dev".equals(warnType)){
			return "主机报警";
		} else if ("net".equals(warnType)){
			return "通讯报警";
		} else if ("fence".equals(warnType)){
			return "入侵报警";
		} else {
			return "未知报警类型";
		}
		
	}
	public String getWarnType() {
		return warnType;
	}
	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}
	
}
