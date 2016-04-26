package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.OperatorLog;
import com.yunjing.entity.OperatorLogDto;

public interface OperatorLogDao {
	
	public void addLog(OperatorLog operatorLog);
	public List<?> getLogPaging(OperatorLogDto operatorLog);
	
	public OperatorLog getLogById(String logId);
	
	/**
	 * 根据操作人查询日志
	 * @param operator
	 * @return
	 */
	public List<OperatorLog> getLogsByOperator(String operator);
	
	public List<?> getAll(OperatorLogDto operatorLog);
	
}
