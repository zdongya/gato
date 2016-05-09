package com.yunjing.entity;

/**
 * @author  2011-12-29
 */
public class Customer extends AbstractUser {
	

	private Integer status = AbstractUser.Status.AVAILABLE.ordinal();

	private String role = AbstractUser.Role.USER_ADMIN.toString();

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
}