package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.OperatorLog;
import com.yunjing.entity.OperatorLogDto;
public interface OperatorLogService {
	
	public abstract List<?> getLogPaging(OperatorLogDto operatorLog);
	
	public abstract OperatorLog getById(String id);
	
	public abstract List<OperatorLog> getByOperator(String operator);
	
	public abstract List<?> getAll(OperatorLogDto operatorLog);
	

}
