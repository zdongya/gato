package com.yunjing.util;

public class BannerImg {
	private String img;
	private String url;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BannerImg(String img, String url) {
		super();
		this.img = img;
		this.url = url;
	}
	
}
