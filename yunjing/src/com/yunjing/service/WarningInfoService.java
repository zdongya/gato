package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.WarningInfo;
import com.yunjing.entity.WarningInfoDto;

public interface WarningInfoService {
	
	public abstract List<?> getAllPaging(WarningInfoDto warnIngInfo);
	
//	public abstract WarningInfo getByDeviceNo(String deviceNo);
	
	public abstract List<WarningInfo> getByZoneName(String zoneName);
	
	public abstract List<?> getAll(WarningInfoDto warnIngInfo);

}
