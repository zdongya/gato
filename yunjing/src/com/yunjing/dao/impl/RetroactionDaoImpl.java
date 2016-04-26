package com.yunjing.dao.impl;

import java.util.List;

import com.yunjing.dao.BaseDao;
import com.yunjing.dao.RetroactionDao;
import com.yunjing.entity.Retroaction;
import com.yunjing.entity.RetroactionDto;
import com.yunjing.util.CheckUtil;

public class RetroactionDaoImpl extends BaseDao implements RetroactionDao {

	@Override
	public List<?> getAllPaging(RetroactionDto retroaction) {
		if (null == retroaction){
			retroaction = new RetroactionDto();
		}
		StringBuilder builder = new StringBuilder("FROM Retroaction as model where 1=1 ");
		if (!CheckUtil.isNullString(retroaction.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')>= '").append(retroaction.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(retroaction.getEndDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')<= '").append(retroaction.getEndDate()).append("'");
		}
		builder.append(" order by model.addDate desc");
		return super.getAllPaging(builder.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Retroaction getById(String id) {
		List<Retroaction> retroactions = super.find("from Retroaction as m where m.id=?", id);
		return retroactions.isEmpty()?null:retroactions.get(0);
	}
	
	@Override
	public List<?> getAll(RetroactionDto retroaction) {
		if (null == retroaction){
			retroaction = new RetroactionDto();
		}
		StringBuilder builder = new StringBuilder("FROM Retroaction as model where 1=1 ");
		if (!CheckUtil.isNullString(retroaction.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')>= '").append(retroaction.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(retroaction.getEndDate())){
			builder.append(" and DATE_FORMAT(model.createTime,'%Y-%m-%d')<= '").append(retroaction.getEndDate()).append("'");
		}
		builder.append(" order by model.addDate desc");
		return super.find(builder.toString());
	}


}
