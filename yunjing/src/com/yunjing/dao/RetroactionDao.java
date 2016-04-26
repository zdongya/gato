package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.Retroaction;
import com.yunjing.entity.RetroactionDto;

public interface RetroactionDao {

	List<?> getAllPaging(RetroactionDto retroaction);

	Retroaction getById(String id);

	List<?> getAll(RetroactionDto retroaction);
	
}
