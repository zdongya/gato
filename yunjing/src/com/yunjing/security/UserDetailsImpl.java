package com.yunjing.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.yunjing.entity.AbstractUser;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 9122598375234290532L;
	
	private AbstractUser detail;

	// 返回权限列表
	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(detail.getRole());
		return new GrantedAuthority[] { grantedAuthority };
	}

	// 返回密码
	public String getPassword() {
		try {
			return detail.getPassword();
		} catch (Exception e) {
			return null;
		}
	}

	// 返回用户名
	public String getUsername() {
		return detail.getUsername();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public UserDetailsImpl(AbstractUser detail) {
		super();
		this.detail = detail;
	}
	
	public AbstractUser getDetail() {
		return detail;
	}
}
