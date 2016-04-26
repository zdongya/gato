package com.yunjing.security;
import org.springframework.security.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {

	private static final long serialVersionUID = -1410313750922151753L;
	
	private String code;

	public GrantedAuthorityImpl(String code) {
		this.code = code;
	}

	public String getAuthority() {
		return code;
	}

	public int compareTo(Object t) {
		return code.compareTo((String) t);
	}
}