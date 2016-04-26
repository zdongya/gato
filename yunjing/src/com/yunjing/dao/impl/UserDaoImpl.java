package com.yunjing.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.yunjing.dao.UserDao;
import com.yunjing.entity.AbstractUser;

/**
 * @author  2011-12-29
 */
public class UserDaoImpl extends HibernateTemplate implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public AbstractUser getByName(String name) {
		String sql = "FROM AbstractUser AS u WHERE u.username = ? and u.status=1";
		List<AbstractUser> users = super.find(sql, name);
		return users.isEmpty() ? null : users.get(0);
	}

}
