package com.yunjing.page;

/** 
 * @author author:dongya
 * @version time：2012-1-30 下午02:20:29 
 * 
 */
public class PageLocal {
	
	private static ThreadLocal<PageController> pageLocal= new ThreadLocal<PageController>();
	public static PageController getPageLocal(){
		return pageLocal.get();
	}
	public static void setPageLocal(PageController pageController){
		pageLocal.set(pageController);
	}
	

}
