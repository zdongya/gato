package com.yunjing.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.yunjing.util.CheckUtil;

/**
 * 推送
 * @author DONGYA
 *
 */
public class Push {
	private String msgId; //消息编号
	private String msgText; //消息内容
	private String topic; //推送topic
	private String addDate; //添加时间
	private String istate; //推送状态0未推送  1已推送 -1推送失败
	private String pushDate; //推送时间
	private String handleState; //处理状态 0 未处理 1处理成功  2处理失败
	private String pushService; //推送接口 0mqtt推送  1个推  2小米推送
	private String itype; //推送类型  0消警  1布撤防  2报警(客户端)
	private String zoneNo; //防区编号
	private String xmAppId; //小米推送ID
	private int appType; //0android  1ios
	private String pushConfigType; //推送设置
	private String zoneName;
	private String deviceName;
	private int nowAppType; //新的推送id
	
	public int getNowAppType() {
		return nowAppType;
	}
	public void setNowAppType(int nowAppType) {
		this.nowAppType = nowAppType;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getPushConfigType() {
		if (CheckUtil.isNullString(pushConfigType)){
			return "2";
		}
		return pushConfigType;
	}
	public void setPushConfigType(String pushConfigType) {
		this.pushConfigType = pushConfigType;
	}
	public int getAppType() {
		return appType;
	}
	public void setAppType(int appType) {
		this.appType = appType;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getIstate() {
		return istate;
	}
	public void setIstate(String istate) {
		this.istate = istate;
	}
	
	public String getPushService() {
		return pushService;
	}
	public void setPushService(String pushService) {
		this.pushService = pushService;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getPushDate() {
		return pushDate;
	}
	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}
	public String getHandleState() {
		return handleState;
	}
	public void setHandleState(String handleState) {
		this.handleState = handleState;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	public String getItype() {
		return itype;
	}
	public void setItype(String itype) {
		this.itype = itype;
	}
	public String getZoneNo() {
		return zoneNo;
	}
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	public String getXmAppId() {
		return xmAppId;
	}
	public void setXmAppId(String xmAppId) {
		this.xmAppId = xmAppId;
	}
	
}
