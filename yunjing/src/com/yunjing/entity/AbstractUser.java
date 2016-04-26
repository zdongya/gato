package com.yunjing.entity;

import java.sql.Timestamp;

import com.yunjing.util.DateUtil;

/**
 * @author  2011-12-29
 */
public abstract class AbstractUser{

	public enum Status {
		UNAVAILABLE, AVAILABLE;
	}

	public enum Role {
		USER_ADMIN, USER_CUSTOMER,USER_MEMBER
	}

	private String id;

	private String username;
	
	private String password;
	
	private String email;

	private Integer status;

	private String role;
	
	private String realName;
	
	private String cellphone;
	
	private Timestamp addDate = DateUtil.getNowTimestamp(); //添加时间
	

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	
}
