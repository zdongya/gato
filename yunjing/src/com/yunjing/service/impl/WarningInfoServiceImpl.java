package com.yunjing.service.impl;

import java.util.List;

import com.yunjing.dao.WarningInfoDao;
import com.yunjing.entity.WarningInfo;
import com.yunjing.entity.WarningInfoDto;
import com.yunjing.service.WarningInfoService;

public class WarningInfoServiceImpl implements WarningInfoService {
	private WarningInfoDao warnIngInfoDao;

	@Override
	public List<?> getAllPaging(WarningInfoDto warnIngInfo) {
		return warnIngInfoDao.getAllPaging(warnIngInfo);
	}

	@Override
	public List<WarningInfo> getByZoneName(String zoneName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWarnIngInfoDao(WarningInfoDao warnIngInfoDao) {
		this.warnIngInfoDao = warnIngInfoDao;
	}

	@Override
	public List<?> getAll(WarningInfoDto warnIngInfo) {
		return warnIngInfoDao.getAll(warnIngInfo);
	}
	

}
