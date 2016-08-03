package com.yunjing.util;

public class SmsSender {
	private int smsId; //短信ID
	private int itype; //0提交   1发送报告
	private String mobileNo; //手机号
	private boolean result; //发送结果
	private String errDesc = ""; //结果描述
	private String batchId; //批次号
	private int dbFlag; //数据库发送结果
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getErrDesc() {
		return errDesc;
	}
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getItype() {
		return itype;
	}
	public void setItype(int itype) {
		this.itype = itype;
	}
	public int getDbFlag() {
		return dbFlag;
	}
	public void setDbFlag(int dbFlag) {
		this.dbFlag = dbFlag;
	}
	public int getSmsId() {
		return smsId;
	}
	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

}
