package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.RetroactionDto;

public interface RetroactionService {
	
	public abstract List<?> getAllPaging(RetroactionDto retroaction);

	public abstract void alterMemo(String id, String memo);

	List<?> getAll(RetroactionDto retroaction);
	
//	public abstract WarningInfo getByDeviceNo(String deviceNo);
	

}
