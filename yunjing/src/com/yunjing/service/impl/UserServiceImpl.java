package com.yunjing.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.yunjing.dao.UserDao;
import com.yunjing.entity.AbstractUser;
import com.yunjing.security.UserDetailsImpl;

/**
 * @author  2011-12-29
 */
public class UserServiceImpl implements UserDetailsService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws  DataAccessException {
		AbstractUser user;
		try {
			user = userDao.getByName(name);
			return new UserDetailsImpl(user);
		} catch (UsernameNotFoundException e) {
			
			return null;
		}
		
	}

}
