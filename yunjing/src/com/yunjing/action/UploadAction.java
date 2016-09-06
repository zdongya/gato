package com.yunjing.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.BannerImg;
import com.yunjing.service.BannerImgService;
import com.yunjing.util.CallResult;
import com.yunjing.util.Utils;

public class UploadAction extends BaseAction {
	private Integer id;
	private static final long serialVersionUID = 1L;
	private BannerImgService bannerImgService;
	private File uploadFile;
	private List<?> bannerImgs;
	private BannerImg bannerImg;

	/**
	 * 查询所有banner图片信息
	 * 
	 * @return
	 */
	public String index() {
		try {
			bannerImgs = bannerImgService.getAll();
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 修改banner图
	 */
	public void update() {
		CallResult result = new CallResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			bannerImgService.update(bannerImg);
			result.setDesc("修改banner图片成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("修改banner图片失败，系统错误");
		} finally {
			Utils.writerJsonResult(response, result);
		}
	}
	
	/**
	 * 跳转到编辑页面
	 * @return
	 */
	public String toEdit() {
		try {
			bannerImg = bannerImgService.getById(id);
			return "toEdit";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	

	public void setBannerImgService(BannerImgService bannerImgService) {
		this.bannerImgService = bannerImgService;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<?> getBannerImgs() {
		return bannerImgs;
	}

	public void setBannerImgs(List<?> bannerImgs) {
		this.bannerImgs = bannerImgs;
	}

	public BannerImg getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(BannerImg bannerImg) {
		this.bannerImg = bannerImg;
	}

}
