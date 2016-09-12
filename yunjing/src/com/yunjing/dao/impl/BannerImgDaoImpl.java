
package com.yunjing.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.yunjing.dao.BannerImgDao;
import com.yunjing.entity.BannerImg;

public class BannerImgDaoImpl extends HibernateTemplate implements BannerImgDao{

	@Override
	public void deleteById(int id) {
		super.delete(getById(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BannerImg> getAll() {
		String sql = "from BannerImg as m order by m.id asc";
		return super.find(sql);
	}

	@Override
	public BannerImg getById(int id) {
		return super.get(BannerImg.class, id);
	}

	@Override
	public void update(BannerImg bannerImg) {
		super.update(bannerImg);
	}

	@Override
	public void updateVersion(String version) {
		String updateSql = "update BannerImg set iversion=?";
		super.bulkUpdate(updateSql, new Object[]{version});
	}

	@Override
	public void add(BannerImg bannerImg) {
		super.save(bannerImg);
	}

}
