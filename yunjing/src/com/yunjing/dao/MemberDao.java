package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.Member;
import com.yunjing.entity.MemberDto;

public interface MemberDao {
	
	public abstract List<Member> getByUserName(String userName);
	
	public abstract Member getByUserId(String userId);
	
	public abstract List<?> getAllPaging(MemberDto member);
	
	public abstract void updateMemberInfo(Member member);
	
	public abstract List<?> getAll(MemberDto member);
}
