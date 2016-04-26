package com.yunjing.action.image;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.yunjing.dao.image.ImageDao;
import com.yunjing.entity.image.Image;

/** 
 * @author dongya
 * @version 创建时间�?2012-4-1 上午12:01:44 
 * 类说�? 
 */
public class ImageAction extends ActionSupport implements ServletResponseAware{
	private ImageDao imageDao;
	private Image image;
	private HttpServletResponse response;
	
	public String add(){
		Image image = new Image();
		image.setCardNo("412829198804126815");
		try {
			imageDao.save(image);
		} catch (Exception e) {
			
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	
	public void test(){
		try {
			image = imageDao.getImageByCardNo();
			response.getOutputStream().write(image.getPhoto()); 
			response.getOutputStream().flush();    
			response.getOutputStream().close();  

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public String toView(){
		return "view";
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
