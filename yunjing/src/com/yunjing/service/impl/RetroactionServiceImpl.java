package com.yunjing.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yunjing.dao.RetroactionDao;
import com.yunjing.entity.Retroaction;
import com.yunjing.entity.RetroactionDto;
import com.yunjing.service.RetroactionService;

public class RetroactionServiceImpl implements RetroactionService {
	private RetroactionDao retroactionDao;

	@Override
	public List<?> getAllPaging(RetroactionDto retroaction) {
		return retroactionDao.getAllPaging(retroaction);
	}

	public void setRetroactionDao(RetroactionDao retroactionDao) {
		this.retroactionDao = retroactionDao;
	}

	@Override
	@Transactional
	public void alterMemo(String id, String memo) {
		Retroaction retroaction = retroactionDao.getById(id);
		retroaction.setMemo(memo);
	}

	@Override
	public List<?> getAll(RetroactionDto retroaction) {
		return retroactionDao.getAll(retroaction);
	}
}
