package com.yunjing.dao;

import com.yunjing.entity.AbstractUser;

/**
 * @author  2011-12-29
 */
public interface UserDao {

	public AbstractUser getByName(String name);
}
