package com.yunjing.page.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yunjing.page.PageController;
import com.yunjing.page.PageControllerUtils;
import com.yunjing.page.PageLocal;


/** 
 * @author author:dongya
 * @version time：2012-1-30 下午02:30:39 
 * 
 */
public class PageFilter implements Filter {

	@Override
	public void destroy() {
		

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse)res;
		PageController pageController = PageControllerUtils.init(request);
		PageLocal.setPageLocal(pageController);
		chain.doFilter(request, response);
		PageLocal.setPageLocal(null);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
