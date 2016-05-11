package com.yunjing.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yunjing.dao.MemberDao;
import com.yunjing.entity.Member;
import com.yunjing.entity.MemberDto;
import com.yunjing.service.MemberService;
import com.yunjing.util.Utils;

public class MemberServiceImpl implements MemberService {
	private MemberDao memberDao;


	@Override
	public Member getByUserId(String userId) {
		return memberDao.getByUserId(userId);
	}

	@Override
	public List<Member> getByUserName(String userName) {
		return memberDao.getByUserName(userName);
	}

	@Override
	@Transactional
	public void alterMemberPwd(String userId, String pwd) {
		Member member = memberDao.getByUserId(userId);
		String newPwd = Utils.getMD5Str(pwd);
		member.setPassword(newPwd);

	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public List<?> getAllPaging(MemberDto member) {
		return memberDao.getAllPaging(member);
	}

	@Override
	@Transactional
	public void alterMemberMemo(String userId, String memo) {
		Member member = memberDao.getByUserId(userId);
		member.setMemo(memo);
	}

	@Override
	public List<?> getAll(MemberDto member) {
		return memberDao.getAll(member);
	}

	@Override
	public List<Member> getByDeviceNo(String deviceNo) {
		return memberDao.getByDeviceNo(deviceNo);
	}

}
