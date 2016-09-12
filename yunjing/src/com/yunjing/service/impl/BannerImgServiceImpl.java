package com.yunjing.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yunjing.dao.BannerImgDao;
import com.yunjing.entity.BannerImg;
import com.yunjing.service.BannerImgService;

public class BannerImgServiceImpl implements BannerImgService {
	private BannerImgDao bannerImgDao;

	@Override
	public List<BannerImg> getAll() {
		return bannerImgDao.getAll();
	}

	@Override
	public BannerImg getById(int id) {
		return bannerImgDao.getById(id);
	}

	@Override
	@Transactional
	public void update(BannerImg bannerImg) {
		String iversion = System.currentTimeMillis() + "";
		bannerImg.setIversion(iversion);
		bannerImgDao.update(bannerImg);
		bannerImgDao.updateVersion(iversion);
	}

	public void setBannerImgDao(BannerImgDao bannerImgDao) {
		this.bannerImgDao = bannerImgDao;
	}

	@Override
	@Transactional
	public void addBannerImg(String imgName, String imgHref) {
		BannerImg bannerImg = new BannerImg();
		String iversion = System.currentTimeMillis() + "";
		bannerImg.setIversion(iversion);
		bannerImgDao.add(bannerImg);
		bannerImgDao.updateVersion(iversion);
		
	}

}
