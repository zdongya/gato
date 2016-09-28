package com.yunjing.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.BannerImg;
import com.yunjing.service.BannerImgService;
import com.yunjing.util.CallResult;
import com.yunjing.util.Constants;
import com.yunjing.util.Utils;

public class UploadAction extends BaseAction {
	

	private Integer id;
	private static final long serialVersionUID = 1L;
	private BannerImgService bannerImgService;
	private File upload;
	private List<?> bannerImgs;
	private BannerImg bannerImg;
	private String imgName;
	private String imgHref;

	private String uploadFileName;
	private String uploadContentType;

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgHref() {
		return imgHref;
	}

	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}

	/**
	 * 查询所有banner图片信息
	 * 
	 * @return
	 */
	public String index() {
		try {
			bannerImgs = bannerImgService.getAll();
			String errDesc = getErrDesc();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.getSession().setAttribute("errDesc", errDesc);
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
	
	public String toAdd() {
		return "toAdd";
	}
	
	//更新备注
	public void deleteBanner(){
		CallResult result = new CallResult();
		HttpServletResponse response = super.getResponse();
		try {
			bannerImgService.deleteImg(id);
			result.setDesc("更新备注成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("更新备注失败，系统错误");
		} finally {
			Utils.writerJsonResult(response, result);
		}
	}
	
	
	/**
	 * 添加banner图
	 */
	public String add() {
		CallResult result = new CallResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			System.out.println("uploadFileName:" + uploadFileName + ";uploadContentType:" + uploadContentType);
			String fileType = uploadContentType.split("\\/")[0];
			if (fileType.equalsIgnoreCase("image")){
				InputStream is = new FileInputStream(upload);
				OutputStream os = new FileOutputStream(new File(Constants.imageDir, uploadFileName));
				// 因为file是存放在临时文件夹的文件，我们可以将其文件名和文件路径打印出来，看和之前的fileFileName是否相同
		        byte[] buffer = new byte[500];
		        int length = 0;
		        while(-1 != (length = is.read(buffer, 0, buffer.length)))
		        {
		            os.write(buffer);
		        }
		        os.close();
		        is.close();
				bannerImgService.addBannerImg(uploadFileName, imgHref);
				result.setDesc("上传banner图片成功");
				return "uploadSuc";
			} else {
				System.out.println("只能上传图片");
				setErrDesc("上传失败，只能上传图片");
				return "uploadFail";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("上传失败，系统错误");
			setErrDesc("上传失败，系统错误");
			return "uploadFail";
		}
	}

	public void setBannerImgService(BannerImgService bannerImgService) {
		this.bannerImgService = bannerImgService;
	}


	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
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
