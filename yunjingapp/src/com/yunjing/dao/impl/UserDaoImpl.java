package com.yunjing.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunjing.dao.UserDao;
import com.yunjing.model.User;
import com.yunjing.rowmapper.UserRowMapper;
import com.yunjing.util.DateUtil;

@Repository(value="userDao")
public class UserDaoImpl implements UserDao {
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void saveUser(User user) {
		String sqlStr = "insert into tb_user(userid,password,mobileno,wechatid,email,itype,istate,registerdate,token,overtime,username,icoin,nickname,xmappid) values(?,?,?,?,?,?,?,now(),?,?,?,?,?,?)";
		Object[] params = new Object[] {user.getUserId(), user.getPassword(), user.getMobileNo(), user.getWechatId(),user.getEmail(),user.getItype(),user.getIstate(),user.getToken(), DateUtil.getTimestampByString(user.getOverTime()),user.getUserName(),user.getIcoin(),user.getNickName(),user.getXmAppId()};
		jdbcTemplate.update(sqlStr, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByWechat(String wechatId) {
		try {
			String sqlStr = "select * from tb_user where wechatid=?";
			User user = (User)jdbcTemplate.queryForObject(sqlStr, new Object[]{wechatId}, new UserRowMapper());
			return user;
		} catch (DataAccessException e) {
			logger.info("UserServiceImpl[findUserByWechat] 微信号：" + wechatId + "查询结果为空");
			return null;
		}
	}

	@Override
	public void update(User user) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByToken(String token) {
		try {
			String sqlStr = "select * from tb_user where token=? and overtime>now()";
			User user = (User)jdbcTemplate.queryForObject(sqlStr,new Object[]{token}, new UserRowMapper());
			return user;
		} catch (DataAccessException e) {
			logger.info("UserServiceImpl[findUserByToken] token：" + token + "查询结果为空");
			return null;
		}
		
	}

	@Override
	public boolean loginCheck(String mobileNo, String password) {
		String sqlStr = "select count(*) from tb_user where mobileno=? and password=?";
		int count = jdbcTemplate.queryForInt(sqlStr, new Object[]{mobileNo, password});
		return count == 1 ? true:false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserById(String userId) {
		try {
			String sqlStr = "select * from tb_user where userid=?";
			User user = (User) jdbcTemplate.queryForObject(sqlStr, new Object[]{userId}, new UserRowMapper());
			return user;
		} catch (DataAccessException e) {
			logger.info("UserServiceImpl[getUserById] userid：" + userId + "查询结果为空");
			return null;
		}
		
	}

	@Override
	public int getCountByMobile(String mobileNo) {
		String sqlStr = "select count(*) from tb_user where mobileno=?";
		int count = jdbcTemplate.queryForInt(sqlStr, new Object[]{mobileNo});
		return count;
	}

	@Override
	public void updateTokenAfterLogin(String userId, String token, String overTime, String xmAppId) {
		String sqlStr = "update tb_user set token=?,overtime=?,xmappid=? where userid=?";
		Object[] params = new Object[] {token, DateUtil.getTimestampByString(overTime), xmAppId, userId};
		jdbcTemplate.update(sqlStr, params);
	}

	@Override
	public void updateHeadImg(String userId, String imgUrl) {
		String sqlStr = "update tb_user set icoin=? where userid=?";
		jdbcTemplate.update(sqlStr, new Object[]{imgUrl, userId});
		
	}

	@Override
	public User findUserByTokenAndUserId(String token, String userId) {
		try {
			String sqlStr = "select * from tb_user where token=? and userid=? and overtime>now()";
			User user = (User)jdbcTemplate.queryForObject(sqlStr,new Object[]{token, userId}, new UserRowMapper());
			return user;
		} catch (DataAccessException e) {
			logger.info("UserServiceImpl[findUserByToken] token：" + token + "查询结果为空");
			return null;
		}
	}

}
