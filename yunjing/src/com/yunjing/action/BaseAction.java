package com.yunjing.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.yunjing.entity.AbstractUser;
import com.yunjing.security.UserDetailsImpl;

public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware,SessionAware{
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String ,Object> sessionMap;
	private String errDesc = "";
	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
	public static Object getBean(String beanName){
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		return applicationContext.getBean(beanName);
	}
	
	public BaseAction() {
		super();
	}

	public AbstractUser getLoginUser(){
		
		UserDetailsImpl user;
		try {
			user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		return user.getDetail();
	}
	
	/**判断是否登录
	 * @return
	 */
	public boolean getloginFlag(){
		boolean flag = true;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null==authentication)
			flag = false;
		return flag;
		
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		
		this.request = request;
		
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
		
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}	
	
	
	

}
