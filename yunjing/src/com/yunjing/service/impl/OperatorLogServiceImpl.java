package com.yunjing.service.impl;

import java.util.List;

import com.yunjing.dao.OperatorLogDao;
import com.yunjing.entity.OperatorLog;
import com.yunjing.entity.OperatorLogDto;
import com.yunjing.service.OperatorLogService;

public class OperatorLogServiceImpl implements OperatorLogService {
	private OperatorLogDao operatorLogDao;

	@Override
	public List<?> getLogPaging(OperatorLogDto operatorLog) {
		
		return operatorLogDao.getLogPaging(operatorLog);
	}

	@Override
	public OperatorLog getById(String id) {
		return operatorLogDao.getLogById(id);
	}

	@Override
	public List<OperatorLog> getByOperator(String operator) {
		return operatorLogDao.getLogsByOperator(operator);
	}

	public void setOperatorLogDao(OperatorLogDao operatorLogDao) {
		this.operatorLogDao = operatorLogDao;
	}

	@Override
	public List<?> getAll(OperatorLogDto operatorLog) {
		return operatorLogDao.getAll(operatorLog);
	}
	
	

}
