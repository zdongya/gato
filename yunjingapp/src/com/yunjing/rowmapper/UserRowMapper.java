package com.yunjing.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yunjing.model.User;

@SuppressWarnings("rawtypes")
public class UserRowMapper implements RowMapper{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (!rs.wasNull()){
			User user = new User();
			user.setUserId(rs.getString("userid"));
			user.setMobileNo(rs.getString("mobileno"));
			user.setWechatId(rs.getString("wechatid"));
			user.setEmail(rs.getString("email"));
			user.setItype(rs.getInt("itype"));
			user.setIstate(rs.getInt("istate"));
			user.setRegisterDate(rs.getDate("registerdate").toString());
			user.setToken(rs.getString("token"));
			return user;
		} else {
			return null;
		}
	}

}
