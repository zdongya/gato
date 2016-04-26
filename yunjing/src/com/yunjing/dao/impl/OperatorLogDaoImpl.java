package com.yunjing.dao.impl;

import java.util.List;

import com.yunjing.dao.BaseDao;
import com.yunjing.dao.OperatorLogDao;
import com.yunjing.entity.OperatorLog;
import com.yunjing.entity.OperatorLogDto;
import com.yunjing.util.CheckUtil;

public class OperatorLogDaoImpl extends BaseDao implements OperatorLogDao {

	@Override
	public void addLog(OperatorLog operatorLog) {
		super.save(operatorLog);
	}

	@Override
	public List<?> getLogPaging(OperatorLogDto operatorLog) {
		if (null == operatorLog){
			operatorLog = new OperatorLogDto();
		}
		StringBuilder builder = new StringBuilder("FROM OperatorLog as  model where 1=1");
		if (!CheckUtil.isNullString(operatorLog.getNname())){
			builder.append(" and model.operator.nickName='").append(operatorLog.getNname()).append("'");
		}
		if (!CheckUtil.isNullString(operatorLog.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')>= '").append(operatorLog.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(operatorLog.getEndDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')<= '").append(operatorLog.getEndDate()).append("'");
		}
		if (!CheckUtil.isNullString(operatorLog.getOperatorType())){
			builder.append(" and model.operatorType='").append(operatorLog.getOperatorType()).append("'");
		}
		
		builder.append(" order by model.createTime desc");
		System.out.println("queryString:" + builder.toString());
		return super.getAllPaging(builder.toString());
	}

	@Override
	public OperatorLog getLogById(String logId) {
		@SuppressWarnings("unchecked")
		List<OperatorLog> logs = super.find("from OperatorLog as m where m.id=?", logId);
		return logs.isEmpty()?null:logs.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OperatorLog> getLogsByOperator(String operator) {
		String hql = "from OperatorLog as model where model.operator.userId=?";
		List<OperatorLog> logs = super.find(hql, operator);
		return logs;
	}

	
	
	public List<?> getAll(OperatorLogDto operatorLog) {
		if (null == operatorLog){
			operatorLog = new OperatorLogDto();
		}
		StringBuilder builder = new StringBuilder("FROM OperatorLog as  model where 1=1");
		if (!CheckUtil.isNullString(operatorLog.getNname())){
			builder.append(" and model.operator.nickName='").append(operatorLog.getNname()).append("'");
		}
		if (!CheckUtil.isNullString(operatorLog.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')>= '").append(operatorLog.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(operatorLog.getEndDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')<= '").append(operatorLog.getEndDate()).append("'");
		}
		if (!CheckUtil.isNullString(operatorLog.getOperatorType())){
			builder.append(" and model.operatorType='").append(operatorLog.getOperatorType()).append("'");
		}
		
		builder.append(" order by model.createTime desc");
		return super.find(builder.toString());
	}

}
