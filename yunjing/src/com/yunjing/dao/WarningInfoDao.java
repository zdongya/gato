package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.WarningInfoDto;

public interface WarningInfoDao {

	List<?> getAllPaging(WarningInfoDto warnIngInfo);
	List<?> getAll(WarningInfoDto warnIngInfo);
	
}
