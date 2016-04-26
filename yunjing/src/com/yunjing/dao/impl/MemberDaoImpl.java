package com.yunjing.dao.impl;

import java.util.List;

import com.yunjing.dao.BaseDao;
import com.yunjing.dao.MemberDao;
import com.yunjing.entity.Member;
import com.yunjing.entity.MemberDto;
import com.yunjing.util.CheckUtil;

public class MemberDaoImpl extends BaseDao implements MemberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getByUserName(String userName) {
		String sql = "from Member as m where m.userName=?";
		return super.find(sql, userName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Member getByUserId(String userId) {
		List<Member> members = super.find("from Member as m where m.userId=?", userId);
		return members.isEmpty()?null:members.get(0);
	}

	@Override
	public List<?> getAllPaging(MemberDto member) {
		if (null == member){
			member = new MemberDto();
		}
		StringBuilder builder = new StringBuilder("FROM Member as model where 1=1 ");
		if (!CheckUtil.isNullString(member.getNickName())){
			builder.append(" and model.nickName like '%").append(member.getNickName()).append("%'");
		}
		if (!CheckUtil.isNullString(member.getMobileNo())){
			builder.append(" and model.mobileNo='").append(member.getMobileNo()).append("'");
		}
		if (!CheckUtil.isNullString(member.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.registerDate,'%Y-%m-%d')>= '").append(member.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(member.getEndDate())){
			builder.append(" and DATE_FORMAT(model.registerDate,'%Y-%m-%d')<= '").append(member.getEndDate()).append("'");
		}
		builder.append(" order by model.registerDate desc");
		return super.getAllPaging(builder.toString());
	}
	
	public List<?> getAll(MemberDto member) {
		if (null == member){
			member = new MemberDto();
		}
		StringBuilder builder = new StringBuilder("FROM Member as model where 1=1 ");
		if (!CheckUtil.isNullString(member.getNickName())){
			builder.append(" and model.nickName like '%").append(member.getNickName()).append("%'");
		}
		if (!CheckUtil.isNullString(member.getMobileNo())){
			builder.append(" and model.mobileNo='").append(member.getMobileNo()).append("'");
		}
		if (!CheckUtil.isNullString(member.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.registerDate,'%Y-%m-%d')>= '").append(member.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(member.getEndDate())){
			builder.append(" and DATE_FORMAT(model.registerDate,'%Y-%m-%d')<= '").append(member.getEndDate()).append("'");
		}
		builder.append(" order by model.registerDate desc");
		return super.find(builder.toString());
		
	}

	@Override
	public void updateMemberInfo(Member member) {
		super.update(member);
	}

}
