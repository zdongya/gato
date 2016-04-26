package com.yunjing.entity;

public class WarningInfoDto extends WarningInfo {
	private String beginDate = "";
	private String endDate = "";
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
