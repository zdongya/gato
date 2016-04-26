package com.yunjing.entity.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.misc.BASE64Encoder;

/** 
 * @author dongya
 * @version 创建时间：2012-3-31 下午11:11:31 
 * 类说明 
 */
public class Image {
	private  String cardNo;
	private byte[] photo;
	private String base64Image;
	private String filePath = "C:\\Users\\DONGYA\\Pictures\\2008-09\\mm.jpg";
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public String getBase64Image() throws Exception{
		File file = new File(filePath);
		byte[] binary = new byte[(int)file.length()];     
		System.out.println(file.getName());
		InputStream ins = new FileInputStream(file);    
		ins.read(binary);     
		ins.close();               
		base64Image = BASE64Encoder.class.newInstance().encode(binary);   
		return base64Image;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
