package com.yunjing.action;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 系统首页
 * @author DONGYA
 *
 */
public class IndexAction extends BaseAction implements ApplicationContextAware{
	
	private ApplicationContext context ;
	

	public String execute(){
		return "toIndex";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	

}
