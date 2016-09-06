package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.BannerImg;

public interface BannerImgService {
	public List<BannerImg> getAll();
	public BannerImg getById(int id);
	/**
	 * 更新banner图
	 * @param bannerImg
	 */
	public void update(BannerImg bannerImg);

}
