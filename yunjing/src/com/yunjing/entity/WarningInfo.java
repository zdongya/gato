package com.yunjing.entity;

public class WarningInfo {
	private String warningId;
	private String zoneNo;
	private String warnDate = "";
	private String istate = ""; //警报状态0 未解决  1已解决  2误报
	private String handleDate;
	private String memo;
	private String warnType; //报警类型
	private String handler;
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
	public String getWarnDate() {
		return warnDate;
	}
	public void setWarnDate(String warnDate) {
		this.warnDate = warnDate;
	}
	public String getIstate() {
		return istate;
	}
	public void setIstate(String istate) {
		this.istate = istate;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getWarnType() {
		return warnType;
	}
	public void setWarnType(String warnType) {
		this.warnType = warnType;
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
	public String getStateName(){
		if (istate.equals("0")){
			return "未解决";
		} else if (istate.equals("1")){
			return "已解决";
		} else if (istate.equals("2")){
			return "误报";
		} else {
			return "";
		}
	}
	
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
}
