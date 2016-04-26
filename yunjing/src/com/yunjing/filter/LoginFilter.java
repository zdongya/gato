package com.yunjing.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;


/** 
 * @author dongya
 * @version 创建时间：2012-3-22 下午05:14:28 
 * 类说明 :单用户访问登录页面或提交登录的时候验证是否登录，若已登录则直接返回首页
 */
public class LoginFilter implements Filter {


	@Override
	public void destroy() {
		System.out.println("登录拦截器销毁。。。");

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (request.getRequestURI().indexOf("channelInter") != -1){
			chain.doFilter(request, response);
		} else {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			HttpSession session = request.getSession(false);
			if (null == authentication) {		
				if(null==session.getAttribute("flag")){
					session.setAttribute("flag", true);
					response.sendRedirect(request.getContextPath()+"/login_index.html");
				}
				
			}else{//用户已经登录
				response.sendRedirect(request.getContextPath()+"/index.html");
			}

			chain.doFilter(request, response);
		}

	}
	
	

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("登录拦截器启动。。。");

	}

	

}
