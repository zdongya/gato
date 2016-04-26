package com.yunjing.entity;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yunjing.util.DateUtil;



/**
 * @author 2011-12-15 Just for package
 */
public class OperatorLog {

	private String id;
	private String memo; //操作内容描述
	private String ipAddr;
	private String operatorType = ""; //1报警处理 2布防操作 3撤防操作
	private Timestamp createTime = DateUtil.getNowTimestamp(); //操作时间
	
	private Member operator = new Member(); //操作人

	private Zone zone = new Zone();
	private Device device = new Device();

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}



	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Member getOperator() {
		return operator;
	}

	public void setOperator(Member operator) {
		this.operator = operator;
	}



	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public String getTypeName(){
		if (getOperatorType().equals("1")){
			return "报警处理";
		} else if (getOperatorType().equals("2")){
			return "布防操作";
		} else if (getOperatorType().equals("3")){
			return "撤防操作";
		} else {
			return "";
		}
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
