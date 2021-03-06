package com.yunjing.action;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

public class LayoutAction extends BaseAction {
	
	public String top(){
		return "top";
	}
	
	public String left(){
		return "left";
	}
	
	public String main(){
		String result = "indexMain";
		try {		
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();SecurityContextHolder.getContext().getAuthentication();
			if("admin".equals(authentication.getName()))
				result = "adminIndexMain";
		} catch (Exception e) {		
			System.out.println("尚未登录!");
		}
		return result;
	}

}
