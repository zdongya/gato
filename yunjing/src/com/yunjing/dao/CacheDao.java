package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.UserIdMap;


/**
 * 缓存dao
 * @author DONGYA
 */
public interface CacheDao {
	/**
	 * 获取userId--->id map
	 * @param id
	 * @return
	 */
	public List<UserIdMap> getUserIdMap(int id);
}
