package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.BannerImg;

public interface BannerImgDao {
	
	public void deleteById(int id);
	public List<BannerImg> getAll();
	public BannerImg getById(int id);
	public void update(BannerImg bannerImg);
	public void updateVersion(String version);
	public void add(BannerImg bannerImg);

}
