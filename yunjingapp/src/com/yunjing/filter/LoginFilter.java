package com.yunjing.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.yunjing.service.UserService;
/**
 * 登录拦截器
 * @author DONGYA
 *
 */
public class LoginFilter implements Filter{
//	private static String notShouldLoginUrl = "Login,login,start,sendYzm,Register";
	
	private UserService userService;

	@Override
	public void destroy() {
		System.out.println("登录拦截器销毁");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		String requestUri = request.getRequestURI();
		if (requestUri.indexOf("Login")==-1 && requestUri.indexOf("login") == -1 
				&& requestUri.indexOf("start") == -1 && requestUri.indexOf("sendYzm") == -1 && requestUri.indexOf("Register") == -1
				&& requestUri.indexOf("forgeSetPwd") == -1&& requestUri.indexOf("uploadHeadImg") == -1&& requestUri.indexOf("test") == -1
				){ //排除登录接口和启动接口
			//文件上传接口无法获取参数
			String token = request.getParameter("token");
			String userId = request.getParameter("userId");
			System.out.println("token:" + token);
			boolean loginFlag = userService.checkToken(token, userId);
			if (loginFlag){
				chain.doFilter(request, response);
			} else { //登录失败
				JSONObject result = new JSONObject();
				result.put("code", "-6666");
				result.put("desc", "未登录");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
				try {
				    out = response.getWriter();
				    out.write(result.toString());
				} catch (IOException e) {
				    e.printStackTrace();
				} finally {
				    if (out != null) {
				        out.close();
				    }
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext sc = config.getServletContext();
		XmlWebApplicationContext ctx = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
		if (null == userService){
			userService = (UserService)ctx.getBean("userService");
		}
			        
	}

}
