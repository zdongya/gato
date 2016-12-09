package com.yunjing.page;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.yunjing.page.tag.PageControllerTag;



/**
 * 类名:PageControllerUtils.java
 * @author dongya
 * 2009-11-9
 */
public class PageControllerUtils {
	
	//使用指定类创建
	public static PageController init(HttpServletRequest request){
		return PageControllerUtils.init(request, null, 30);
	}
	
	//使用指定类创建并设置分页大小
	public static PageController init(HttpServletRequest request,int pageSize){
		return PageControllerUtils.init(request, null, pageSize);
	}
	
	//使用默认类创建
	public static PageController init(HttpServletRequest request,Class pageController)
	{
		return PageControllerUtils.init(request, pageController, 30);
	}
	
	//使用默认类创建并设置分页大小
	public static PageController init(HttpServletRequest request,Class pageController,int pageSize){
		Set exculdeParams = new HashSet();
		//PAGE_CONTROLLER_CURRENTINDEX强制过滤
		exculdeParams.add(PageControllerTag.PAGE_CONTROLLER_CURRENTINDEX);
		
		// 获得currentIndexTemp当前页
		String currentIndexTemp = request.getParameter(PageControllerTag.PAGE_CONTROLLER_CURRENTINDEX);
		// 获得提交分页地址
		String formUri = request.getRequestURI();
		request.setAttribute(PageControllerTag.REQUST_URI, formUri);
		// 将参数中的重复的 pageController_currentIndex参数过滤掉
		Map targertAttributes = decorateParamMap(request.getParameterMap(),exculdeParams);
		request.setAttribute(PageControllerTag.REQUEST_ATTRIBUTES, targertAttributes);
		// 如不是第一次访问,设置当前分页数据索引
		int currentIndex = (currentIndexTemp == null|| currentIndexTemp.equals(""))? 0 : Integer.parseInt(currentIndexTemp);
		// 创建分页对象,填充当前索引
		PageController result = null;
		try
		{
			if(pageController!=null)
			{
				//使用指定实现类
				result = (PageController)pageController.newInstance();
				result.setCurrentIndex(currentIndex);
				result.setPageSize(pageSize);
			}
			else
				//使用默认实现类
				result = new PageControllerImpl(pageSize,currentIndex);
		}
		catch(InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch(IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		// 将分页对象放入request
		request.setAttribute(PageControllerTag.PAGECONTROLLER_IN_REQUEST,result);
		return result;
	}
	
	/**
	 * 将参数中的重复的 pageController_currentIndex参数过滤掉
	 * @param oriAttributes
	 * @return
	 */
	protected static Map decorateParamMap(Map oriAttributes,Set exculdeParams){
		Map targertAttributes = new HashMap(oriAttributes);
		Iterator iter = exculdeParams.iterator();
		while(iter.hasNext()){
			targertAttributes.remove(iter.next());
		}
		return targertAttributes;
	}
	
}
