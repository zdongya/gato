package com.yunjing.page.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

import com.yunjing.page.PageController;
import com.yunjing.page.freemarker.FreeMarkerUtils;


public class PageControllerTag implements SimpleTag {

	//模板字体大小
	public static final int _FRONT_SIZE = 3;
	
	//模板字体
	public static final String FRONT_SIZE = "frontSize";
	
	//模板名
	public static final String TEMPLATE_NAME = "PageForm.ftl";
	
	//请求URI
	public static final String REQUST_URI = "requestUri";
	
	//分页索引在requset.attribute中的名称
	public static final String PAGE_CONTROLLER_CURRENTINDEX = "_pageController_currentIndex";
	
	//查询参数在requset.attribute中的名称
	public static final String REQUEST_ATTRIBUTES= "request_attributes";
	
	//当前页
	public static final String CURRENTPAGE = "currentPage";
	
	//总页数
	public static final String TOTALPAGE = "totalPage";
	
	//下一页索引
	public static final String NEXTINDEX = "nextIndex";
	
	//上一页索引
	public static final String PREVIOUSINDEX = "previousIndex";
	
	//首页索引
	public static final String STARTINDEX = "startIndex";
	
	//尾页索引
	public static final String LASTINDEX = "lastIndex";
	
	//分页数据大小
	public static final String INDEXSIZE = "indexSize";
	
	//分页尺寸大小
	public static final String PAGESIZE = "pageSize";
	
	//PageController在request请求中的名称
	public static final String PAGECONTROLLER_IN_REQUEST = "pageController_in_request";
	
	//项目目录
	public static final String CONTEXTPATH = "contextPath";
	
	public static final String REQUEST_METHOD = "requestMethod";
	
	public JspContext jspContext;

	//添加系统FreeMarker插值数据
	private Map createDatas(PageController pageController,String formUri, String method, Map attributes,String contextPath)
	{
		Map datas = new HashMap();
		datas.put(PageControllerTag.REQUST_URI,formUri);
		datas.put(PageControllerTag.REQUEST_ATTRIBUTES,attributes);
		datas.put(PageControllerTag.CURRENTPAGE,pageController.getCurrentPage());
		datas.put(PageControllerTag.TOTALPAGE, pageController.getTotalPage());
		datas.put(PageControllerTag.STARTINDEX,0);
		datas.put(PageControllerTag.LASTINDEX,pageController.getLastIndex());
		datas.put(PageControllerTag.PREVIOUSINDEX, pageController.getPreviousIndex());
		datas.put(PageControllerTag.NEXTINDEX, pageController.getNextIndex());
		datas.put(PageControllerTag.FRONT_SIZE,PageControllerTag._FRONT_SIZE);
		datas.put(PageControllerTag.PAGESIZE,pageController.getPageSize());
		datas.put(PageControllerTag.INDEXSIZE,pageController.getIndexSize());
		datas.put(PageControllerTag.REQUST_URI,formUri);
		datas.put(PageControllerTag.REQUEST_METHOD,method);
		datas.put(PageControllerTag.CONTEXTPATH, contextPath);
		return datas;
	}
	
	
	public void setJspBody(JspFragment jspBody) {
	}

	public void setJspContext(JspContext jspContext) {
		this.jspContext = jspContext;
	}

	public JspTag getParent() {
		return null;
	}

	public void setParent(JspTag parent) {
	}

	public void doTag() throws JspException, IOException {	
		PageContext pageContext = (PageContext) jspContext;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		//获得提交表单的URI
		String formUri = (String)request.getAttribute(PageControllerTag.REQUST_URI);
		//获得表单所有参数
		Map attributes = (Map)request.getAttribute(PageControllerTag.REQUEST_ATTRIBUTES);
		//初始化PageController
		PageController pageController = (PageController)request.getAttribute(PageControllerTag.PAGECONTROLLER_IN_REQUEST);
		//request中不存在pageController不显示分页
		
		//项目目录
		String contextPath = (String)request.getContextPath();
		if (pageController != null)
		{
			//填充分页数据
			Map datas = this.createDatas(pageController,formUri,request.getMethod(), attributes,contextPath);
			Writer writer = jspContext.getOut();
			FreeMarkerUtils.merge(PageControllerTag.TEMPLATE_NAME, datas, jspContext.getOut());
		}
	}
}
