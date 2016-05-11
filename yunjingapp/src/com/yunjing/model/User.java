package com.yunjing.model;

public class User extends BaseModel{
	private String userName; //用户名
	private String icoin; //头像地址
	private String nickName; 
	private String password;
	private String mobileNo;
	private String wechatId;
	private String email;
	private int itype = 1; //0手机 1微信
	private int istate = 1; //1正常用户 0无效用户
	private String registerDate; //用户注册时间
	private String overTime; //token过期时间
	private String loginDate; //登录时间
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
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
	public int getItype() {
		return itype;
	}
	public void setItype(int itype) {
		this.itype = itype;
	}
	public int getIstate() {
		return istate;
	}
	public void setIstate(int istate) {
		this.istate = istate;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
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
	
	

}
