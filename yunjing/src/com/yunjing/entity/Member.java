package com.yunjing.entity;

import java.sql.Timestamp;

/**
 * APP用户
 * @author DONGYA
 *
 */
public class Member {
	private String userId;
	private String password;
	private String mobileNo;
	private String wechatId;
	private String email;
	private Integer itype ; //0手机  1微信
	private Integer istate; //0无效用户 1正常用户
	private Timestamp registerDate;
	private String token;
	private Timestamp overTime;
	private String userName;
	private String icoin;
	private String nickName = "";
	private String xmAppId; //小米推送id
	private String memo = ""; //备注 
	private String loginDate; //登录时间
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
	}
	public Integer getIstate() {
		return istate;
	}
	public void setIstate(Integer istate) {
		this.istate = istate;
	}
	public Timestamp getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getOverTime() {
		return overTime;
	}
	public void setOverTime(Timestamp overTime) {
		this.overTime = overTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIcoin() {
		return icoin;
	}
	public void setIcoin(String icoin) {
		this.icoin = icoin;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getTypeName(){
		if(null == getItype()){
			return "";
		} else if (getItype() == 0){
			return "手机注册";
		} else if(getItype() == 1){
			return "微信注册";
		} else {
			return "未知";
		}
	}
	
	public String getXmAppId() {
		return xmAppId;
	}
	public void setXmAppId(String xmAppId) {
		this.xmAppId = xmAppId;
	}
	public String getStateName(){
		if(null == getIstate()){
			return "";
		} else if (getIstate() == 0){
			return "无效用户";
		} else if (getIstate() == 1){
			return "正常用户";
		} else {
			return "未知";
		}
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
}
