package com.yunjing.dao.impl;

import java.util.List;

import com.yunjing.dao.BaseDao;
import com.yunjing.dao.WarningInfoDao;
import com.yunjing.entity.WarningInfoDto;
import com.yunjing.util.CheckUtil;

public class WarningInfoDaoImpl extends BaseDao implements WarningInfoDao {

	@Override
	public List<?> getAllPaging(WarningInfoDto warnIngInfo) {
		if (null == warnIngInfo){
			warnIngInfo = new WarningInfoDto();
		}
		StringBuilder builder = new StringBuilder("FROM WarningInfo as model where 1=1 ");
		if (!CheckUtil.isNullString(warnIngInfo.getIstate())){
			builder.append(" and model.istate='").append(warnIngInfo.getIstate()).append("'");
		}
		if (!CheckUtil.isNullString(warnIngInfo.getBeginDate())){
			String beginDate = warnIngInfo.getBeginDate() + " 00:00:00";
			builder.append(" and model.warnDate>= '").append(beginDate).append("'");
		}
		if (!CheckUtil.isNullString(warnIngInfo.getEndDate())){
			String endDate = warnIngInfo.getEndDate() + " 23:59:59";
			builder.append(" and model.warnDate <= '").append(endDate).append("'");
		}
		builder.append(" order by model.warnDate desc");
		System.out.println("queryString:" + builder.toString());
		return super.getAllPaging(builder.toString());
	}
	
	public List<?> getAll(WarningInfoDto warnIngInfo) {
		if (null == warnIngInfo){
			warnIngInfo = new WarningInfoDto();
		}
		StringBuilder builder = new StringBuilder("FROM WarningInfo as model where 1=1 ");
		if (!CheckUtil.isNullString(warnIngInfo.getIstate())){
			builder.append(" and model.istate='").append(warnIngInfo.getIstate()).append("'");
		}
		if (!CheckUtil.isNullString(warnIngInfo.getBeginDate())){
			String beginDate = warnIngInfo.getBeginDate() + " 00:00:00";
			builder.append(" and model.warnDate>= '").append(beginDate).append("'");
		}
		if (!CheckUtil.isNullString(warnIngInfo.getEndDate())){
			String endDate = warnIngInfo.getEndDate() + " 23:59:59";
			builder.append(" and model.warnDate <= '").append(endDate).append("'");
		}
		builder.append(" order by model.warnDate desc");
		return super.find(builder.toString());
	}

}
