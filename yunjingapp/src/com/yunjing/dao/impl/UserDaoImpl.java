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

//	@Override
//	public void saveUser(User user) {
//		String sqlStr = "insert into tb_user(userid,password,mobileno,wechatid,email,itype,istate,registerdate,token,overtime,username,icoin,nickname,xmappid,logindate,apptype) values(?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?)";
//		Object[] params = new Object[] {user.getUserId(), user.getPassword(), user.getMobileNo(), user.getWechatId(),user.getEmail(),user.getItype(),user.getIstate(),user.getToken(), DateUtil.getTimestampByString(user.getOverTime()),user.getUserName(),user.getIcoin(),user.getNickName(),user.getXmAppId(), user.getLoginDate(),user.getAppType()};
//		jdbcTemplate.update(sqlStr, params);
//	}
	
	
	@Override
	public void saveUser(User user) {
		String sqlStr = "insert into tb_user(id,userid,password,mobileno,wechatid,email,itype,istate,registerdate,token,overtime,username,icoin,nickname,xmappid,logindate,apptype) "
				+ "select (select max(id)+1 from tb_user),?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,? from dual";
		Object[] params = new Object[] {user.getUserId(), user.getPassword(), user.getMobileNo(), user.getWechatId(),user.getEmail(),user.getItype(),user.getIstate(),user.getToken(), DateUtil.getTimestampByString(user.getOverTime()),user.getUserName(),user.getIcoin(),user.getNickName(),user.getXmAppId(), user.getLoginDate(),user.getAppType()};
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
	public void updateTokenAfterLogin(String userId,String appType, String token, String overTime, String xmAppId, String loginDate) {
		String sqlStr = "update tb_user set NOWAPPTYPE=?, token=?,overtime=?,xmappid=?,logindate=? where userid=?";
		Object[] params = new Object[] {appType, token, DateUtil.getTimestampByString(overTime), xmAppId, loginDate, userId};
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

	@Override
	public boolean checkUserMobile(String mobileNo) {
		String sqlStr = "select count(*) from tb_user where mobileno=?";
		int num = jdbcTemplate.queryForInt(sqlStr, new Object[]{mobileNo});
		if (num == 1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User getMobLoginUser(String mobileNo, String passWord) {
		try {
			String sqlStr = "select * from tb_user where mobileno=? and password=?";
			User user = (User)jdbcTemplate.queryForObject(sqlStr, new Object[]{mobileNo, passWord}, new UserRowMapper());
			return user;
		} catch (DataAccessException e) {
			logger.info("UserServiceImpl[getMobLoginUser] 获取到用户为空，mobileNo:" + mobileNo);
			return null;
		}
	}

	@Override
	public void addSms(String mobileNo, String yzm, String smsContent, String service, int type) {
		String sqlStr = "insert into tb_sms(mobileNo,smsContent,addDate,service,smsType,randomCode) values(?,?,now(),?,?,?)";
		Object[] params = new Object[] {mobileNo, smsContent, service, type, yzm};
		jdbcTemplate.update(sqlStr, params);
	}

	@Override
	public boolean checkYzm(String mobileNo, String yzm, int type) {
		String sqlStr = "select count(*) from (SELECT * FROM tb_sms ORDER BY addDate DESC LIMIT 0,1) a where a.mobileNo=? and a.randomCode=? and a.smsType=?";
		int num = jdbcTemplate.queryForInt(sqlStr, new Object[]{mobileNo, yzm, type});
		if (num == 1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updatePwd(String mobileNo, String pwd) {
		String sqlStr = "update tb_user set password=? where mobileno=?";
		jdbcTemplate.update(sqlStr, new Object[]{pwd, mobileNo});
	}

	@Override
	public void updatePwd(String mobileNo, String oldPwd, String pwd) {
		String sqlStr = "update tb_user set password=? where mobileno=? and password=?";
		jdbcTemplate.update(sqlStr, new Object[]{pwd, mobileNo, oldPwd});
	}

	@Override
	public void updateNickName(String userId, String nickName) {
		String sqlStr = "update tb_user set nickname=? where userid=?";
		jdbcTemplate.update(sqlStr, new Object[]{nickName, userId});
	}
	
	@Override
	public void updateXmAppId(String userId,String appType, String xmAppId) {
		String sql = "update tb_user set nowapptype=?, xmappid=? where userid=?";
		Object[] params = new Object[] {appType, xmAppId, userId};
		jdbcTemplate.update(sql, params);
	}


}
