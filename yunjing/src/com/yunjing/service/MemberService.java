package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.Member;
import com.yunjing.entity.MemberDto;
public interface MemberService {
	
	public abstract List<?> getAllPaging(MemberDto member);
	
	public abstract Member getByUserId(String userId);
	
	public abstract List<Member> getByUserName(String userName);
	
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param pwd
	 */
	public abstract void alterMemberPwd(String userId,String pwd);

	public abstract void alterMemberMemo(String userId, String newpwd);

	public abstract List<?> getAll(MemberDto member);
	
	public abstract List<Member> getByDeviceNo(String deviceNo);

}
